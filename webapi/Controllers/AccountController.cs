using Microsoft.AspNetCore.Mvc.ApplicationModels;
using Microsoft.AspNetCore.Mvc.Infrastructure;
using Microsoft.EntityFrameworkCore;
using System.IdentityModel.Tokens.Jwt;
using Microsoft.IdentityModel.Tokens;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using webapi.Models;
using webapi.DTO;
using System.Security.Claims;

namespace webapi.Controllers
{
    [Route("[controller]/[action]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        private readonly ApplicationDbContext? _context;
        private readonly IConfiguration? _configuration;
        private readonly UserManager<TracktUser>? _userManager;
        private readonly SignInManager<TracktUser>? _signInManager;

        public AccountController(ApplicationDbContext? context,IConfiguration? configuration,UserManager<TracktUser>? userManager,SignInManager<TracktUser>? signInManager)
        {
            _context = context;
            _configuration = configuration;
            _userManager = userManager;
            _signInManager = signInManager;
        }

        [HttpPost]
        //[ResponseCache(CacheProfileName = "NoCache")] --- Fix your caching //TODO
        public async Task<ActionResult> Create(CreateAccountDTO input)
        {
            try
            {
                if(ModelState.IsValid)
                {
                    // Check if a user with the same email already exists
                    var existingUser = await _userManager!.FindByEmailAsync(input.Email!);
                    if (existingUser != null)
                    {
                        ModelState.AddModelError("Email", "This email is already in use");
                        return BadRequest(ModelState);
                    }
                    else
                    {
                        var newUser = new TracktUser
                        {
                            Name = input.Name,
                            Email = input.Email
                        };
                        var result = await _userManager!.CreateAsync(newUser, input.Password!);
                        if (result.Succeeded)
                        {
                            //log later //TODO
                            return StatusCode(201, $"User '{newUser.Name}' has been created.");
                        }
                        else
                            throw new Exception(string.Format("Error: {0}", string.Join(", ", result.Errors.Select(e => e.Description))));
                    }
                }
                else
                {
                    var details = new ValidationProblemDetails(ModelState)
                    {
                        Type = "https://tools.ietf.org/html/rfc7231#section-6.5.1",
                        Status = StatusCodes.Status400BadRequest
                    };
                    return new BadRequestObjectResult(details);

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

        [HttpPost]
        public async Task<ActionResult> Login(LoginDTO input)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var user = await _userManager!.FindByEmailAsync(input.Email!);

                    if (user == null || !await _userManager.CheckPasswordAsync(user, input.Password!))
                    { throw new Exception("Invalid login attempt."); }
                    else
                    {
                        var loginCredentials = new SigningCredentials(
                            new SymmetricSecurityKey(
                                System.Text.Encoding.UTF8.GetBytes(_configuration!["JWT:SigningKey"]!)),
                                SecurityAlgorithms.HmacSha256);

                        var claims = new List<Claim>
                    { new Claim(ClaimTypes.Name, user.UserName!) };

                        var jwtObject = new JwtSecurityToken(
                            issuer: _configuration["JWT:Issuer"],
                            audience: _configuration["JWT:Audience"],
                            claims: claims,
                            expires: DateTime.Now.AddSeconds(300),
                            signingCredentials: loginCredentials);

                        var jwtString = new JwtSecurityTokenHandler()
                            .WriteToken(jwtObject);

                        return StatusCode(StatusCodes.Status200OK, jwtString);
                    }
                }
                else
                {
                    var details = new ValidationProblemDetails(ModelState)
                    {
                        Type = "https://tools.ietf.org/html/rfc7231#section-6.5.1",
                        Status = StatusCodes.Status400BadRequest
                    };
                    return new BadRequestObjectResult(details);
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
}
