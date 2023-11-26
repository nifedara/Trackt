using CloudinaryDotNet;
using CloudinaryDotNet.Actions;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Security.Claims;
using webapi.DTO;
using webapi.Models;

namespace webapi.Controllers
{
    [Authorize] // This attribute ensures that only authenticated users can access these endpoints
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class DestinationsController : ControllerBase
    {
        private readonly IConfiguration? _configuration;
        private readonly ApplicationDbContext? _context;
        private readonly UserManager<TracktUser>? _userManager;

        public DestinationsController(ApplicationDbContext? context, IConfiguration? configuration,
            UserManager<TracktUser>? userManager)
        {
            _context = context;
            _configuration = configuration;
            _userManager = userManager;
        }

        [HttpPost]
        public async Task<ActionResult<BaseResponse>> Create([FromForm] DestinationDTO input)
        {
            try
            {
                var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
                var user = await _userManager!.FindByIdAsync(userId!);

                var valid = user != null && ModelState.IsValid;
                if (valid)
                {
                    // get the uploaded image
                    var imgfile = input.Image;
                    //check that it is an image
                    var anImage = imgfile!.ContentType.StartsWith("image");

                    if (anImage)
                    {
                        // Cloudinary
                        var cloudinaryCloudName = _configuration!["Cloudinary:CloudName"];
                        var cloudinaryApiKey = _configuration["Cloudinary:ApiKey"];
                        var cloudinaryApiSecret = _configuration["Cloudinary:ApiSecret"];

                        // Handle image upload to Cloudinary
                        Account account = new(
                            cloudinaryCloudName,
                            cloudinaryApiKey,
                            cloudinaryApiSecret);
                        Cloudinary cloudinary = new(account);

                        //uploader
                        var uploadParams = new ImageUploadParams()
                        {
                            File = new FileDescription(imgfile!.FileName, imgfile.OpenReadStream()),
                            PublicId = $"{input.DestinationName}_{Guid.NewGuid()}", //destination_uniqueId
                        };
                        var uploadImage = cloudinary.Upload(uploadParams);


                        //new destination
                        var newDestination = new Destination
                        {
                            DestinationName = input.DestinationName,
                            ImageUrl = uploadImage.SecureUrl.ToString(), // Use SecureUri for HTTPS URL
                            Budget = input.Budget,
                            Date = input.Date,
                            UserId = userId,
                            User = user
                        };
                        _context?.Destinations.Add(newDestination);
                        await _context!.SaveChangesAsync();

                        var response = new BaseResponse
                        {
                            Status = true,
                            Message = $"Destination '{input.DestinationName}' has been created."
                        };
                        return response;
                        //return StatusCode(201, $"Destination '{input.DestinationName}' has been created.");
                    }
                    else
                    {
                        var response = new BaseResponse
                        {
                            Status = false,
                            Message = "Invalid file type. Please upload an image."
                        };
                        return response;
                    }
                }
                else
                {
                    var details = new ValidationProblemDetails(ModelState)
                    {
                        Type = "https://tools.ietf.org/html/rfc7231#section-6.5.1",
                        Status = StatusCodes.Status400BadRequest
                    };
                    var response = new BaseResponse
                    {
                        Status = valid,
                        Message = details.Detail
                    };
                    return response;
                    //return new BadRequestObjectResult(details);
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
        public async Task<ActionResult<BaseResponse>> Get(int? destinationId)
        {
            try
            {
                //userId
                var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
                var hasId = destinationId.HasValue;

                if (hasId)
                {
                    //destination by id
                    var destination = _context!.Destinations.Where(u => u.UserId == userId && u.DestinationId == destinationId);
                    var valid = destination != null;
                    if (valid)
                    {
                        var response = new BaseResponse
                        {
                            Status = true,
                            Message = "Destination...",
                            Data = await destination!.ToListAsync()
                        };
                        return response;
                        //return Ok(await destination.ToListAsync());
                    }
                    else
                    {
                        var response = new BaseResponse
                        {
                            Status = true,
                            Message = StatusCodes.Status404NotFound.ToString(),
                            Data = await destination!.ToListAsync()
                        };
                        return response;
                    }
                }
                else
                {
                    //all destinations
                    var destinations = _context!.Destinations.Where(u => u.UserId == userId);
                   if (destinations == null || !await destinations.AnyAsync())
                    {
                        var response = new BaseResponse
                        {
                            Status = true,
                            Message = StatusCodes.Status404NotFound.ToString(),
                            Data = await destinations!.ToListAsync()
                        };
                        return response;
                    }
                   else
                    {
                        var response = new BaseResponse
                        {
                            Status = true,
                            Message = "Destinations...",
                            Data = await destinations!.ToListAsync()
                        };
                        return response;
                        //return Ok(await destinations.ToListAsync());
                    }

                }
            }
            catch  (Exception ex)
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
    }


}