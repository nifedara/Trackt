using System.IdentityModel.Tokens.Jwt;
using Microsoft.IdentityModel.Tokens;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using Microsoft.AspNetCore.WebUtilities;
using System.Text;
using Serilog;
using Trackt.Models;
using Trackt.Services;
using Trackt.DTO;

namespace Trackt.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        private readonly ApplicationDbContext? _context;
        private readonly IConfiguration? _configuration;
        private readonly UserManager<TracktUser>? _userManager;
        private readonly SignInManager<TracktUser>? _signInManager;
        private readonly IMailService _mailSender;

        public AccountController(ApplicationDbContext? context, IConfiguration? configuration,
            UserManager<TracktUser>? userManager, SignInManager<TracktUser>? signInManager, IMailService mailSender)
        {
            _context = context;
            _configuration = configuration;
            _userManager = userManager;
            _signInManager = signInManager;
            _mailSender = mailSender;
        }


        [HttpPost]
        //[ResponseCache(CacheProfileName = "NoCache")] --- Fix your caching //TODO
        public async Task<ActionResult<StatusResponse>> Create(CreateAccount input)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var newUser = new TracktUser
                    {
                        Name = input.Name,
                        Email = input.Email,
                        UserName = input.Email
                    };
                    var result = await _userManager!.CreateAsync(newUser, input.Password!);
                    if (result.Succeeded)
                    {
                        //Send Email Confirmation token
                        var token = await _userManager.GenerateEmailConfirmationTokenAsync(newUser);
                        token = WebEncoders.Base64UrlEncode(Encoding.UTF8.GetBytes(token));
                        var confirmationLink = Url.Action("ConfirmEmail", "Account", new { userId = newUser.Id, token }, HttpContext.Request.Scheme);

                        Log.Information("token => {@token}", token);
                        Log.Information("confirmation link =>  {@confirmationLink}", confirmationLink);

                        //email message
                        var newMailRequest = new MailRequest
                        {
                            ToEmail = input.Email,
                            Subject = "Confirm Email",
                            Body = "Here is your confirmation email: " + confirmationLink,
                        };

                        await _mailSender.SendEmail(newMailRequest);

                        //response from registration
                        var response = new StatusResponse
                        {
                            Status = true,
                            Message = $"A confirmation code has been sent to {newUser.Email}."
                        };
                        return response;
                    }
                    else
                    {
                        var response = new StatusResponse
                        {
                            Status = false,
                            Message = new Exception(result.Errors.Select(e => e.Description).First()).Message
                        };
                        return response;
                    }
                }
                else
                {
                    var details = new ValidationProblemDetails(ModelState);

                    var response = new StatusResponse
                    {
                        Status = false,
                        Message = new Exception(details.Title).Message
                    };
                    return response;

                }
            }
            catch (Exception ex)
            {
                var exceptionDetails = new ProblemDetails
                {
                    Detail = ex.Message,
                    Status = StatusCodes.Status500InternalServerError,
                    Type = "https://tools.ietf.org/html/rfc7231#section-6.6.1"
                };
                return StatusCode(StatusCodes.Status500InternalServerError, exceptionDetails);

            }
        }

        [HttpGet]
        public async Task<ActionResult<StatusResponse>> ConfirmEmail(string userId, string? token)
        {
            var user = await _userManager!.FindByEmailAsync(userId);
            if (user == null || userId == null || token == null)
            {
                var response = new StatusResponse
                {
                    Status = false,
                    Message = "User does not exist."
                };
                return response;
            }

            var result = await _userManager.ConfirmEmailAsync(user, token);
            if (result.Succeeded)
            {
                var response = new StatusResponse
                {
                    Status = true,
                    Message = "User email confirmed."
                };
                return response;
            }
            else
            {
                var response = new StatusResponse
                {
                    Status = false,
                    Message = "Email confirmation unsuccessful"
                };
                return response;
            }
        }


        [HttpPost]
        public async Task<ActionResult<StatusResponse>> Login(Login input)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var user = await _userManager!.FindByEmailAsync(input.Email!);
                    var invalid = user == null || !await _userManager.CheckPasswordAsync(user, input.Password!);

                    if (invalid)
                    {
                        var response = new StatusResponse
                        {
                            Status = false,
                            Message = "Your username or password is incorrect"
                        };
                        return response;
                    }
                    else
                    {
                        var loginCredentials = new SigningCredentials(
                            new SymmetricSecurityKey(
                                Encoding.UTF8.GetBytes(_configuration!["JWT:SigningKey"]!)),
                                SecurityAlgorithms.HmacSha256);

                        var claims = new List<Claim>
                    { new Claim(ClaimTypes.NameIdentifier, user!.Id) };

                        var jwtObject = new JwtSecurityToken(
                            issuer: _configuration["JWT:Issuer"],
                            audience: _configuration["JWT:Audience"],
                            claims: claims,
                            expires: DateTime.Now.AddSeconds(3600),
                            signingCredentials: loginCredentials);

                        var jwtString = new JwtSecurityTokenHandler()
                            .WriteToken(jwtObject);

                        var token = jwtString;
                        UserInfo userInfo = new()
                        {
                            Name = user.Name,
                            Email = user.Email,
                        };

                        var response = new StatusResponse
                        {
                            Status = true,
                            Message = "Successfully authenticated.",
                            Data = new
                            {
                                token,
                                userInfo
                            }
                        };
                        return response;
                    }
                }
                else
                {
                    var details = new ValidationProblemDetails(ModelState);

                    var response = new StatusResponse
                    {
                        Status = false,
                        Message = new Exception(details.Title).Message
                    };
                    return response;
                }
            }
            catch (Exception ex)
            {
                var exceptionDetails = new ProblemDetails
                {
                    Detail = ex.Message,
                    Status = StatusCodes.Status401Unauthorized,
                    Type = "https://tools.ietf.org/html/rfc7231#section-6.6.1"
                };
                return StatusCode(StatusCodes.Status401Unauthorized, exceptionDetails);
            }
        }

    }
    public class UserInfo
    {
        public string? Name { get; set; }
        public string? Email { get; set; }
    }
}
