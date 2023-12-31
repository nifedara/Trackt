﻿using System.IdentityModel.Tokens.Jwt;
using Microsoft.IdentityModel.Tokens;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using webapi.Models;
using webapi.DTO;
using System.Security.Claims;

namespace webapi.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        private readonly ApplicationDbContext? _context;
        private readonly IConfiguration? _configuration;
        private readonly UserManager<TracktUser>? _userManager;
        private readonly SignInManager<TracktUser>? _signInManager;

        public AccountController(ApplicationDbContext? context,IConfiguration? configuration,
            UserManager<TracktUser>? userManager,SignInManager<TracktUser>? signInManager)
        {
            _context = context;
            _configuration = configuration;
            _userManager = userManager;
            _signInManager = signInManager;
        }

        [HttpPost]
        //[ResponseCache(CacheProfileName = "NoCache")] --- Fix your caching //TODO
        public async Task<ActionResult<BaseResponse>> Create(CreateAccountDTO input)
        {
            try
            {
                if(ModelState.IsValid)
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
                        var response = new BaseResponse
                        {
                            Status = true,
                            Message = $"User '{newUser.Name}' has been created."
                        };
                        return response;
                    }
                    else
                    {
                        var response = new BaseResponse
                        {
                            Status = false,
                            Message = new Exception(result.Errors.Select(e => e.Description).First()).Message
                        };
                        return response;
                        //log later //TODO.
                    }
                }
                else
                {
                    var details = new ValidationProblemDetails(ModelState);

                    var response = new BaseResponse
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

        [HttpPost]
        public async Task<ActionResult<BaseResponse>> Login(LoginDTO input)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    var user = await _userManager!.FindByEmailAsync(input.Email!);
                    var invalid = user == null || !await _userManager.CheckPasswordAsync(user, input.Password!);

                    if (invalid)
                    {
                        var response = new BaseResponse
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
                                System.Text.Encoding.UTF8.GetBytes(_configuration!["JWT:SigningKey"]!)),
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

                        var response = new BaseResponse
                        {
                            Status = true,
                            Message = "Successfully authenticated.",
                            Data = new
                            {
                                token, userInfo
                            }
                        };
                        return response;
                    }
                }
                else
                {
                    var details = new ValidationProblemDetails(ModelState);

                    var response = new BaseResponse
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
