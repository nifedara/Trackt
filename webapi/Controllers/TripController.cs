using CloudinaryDotNet;
using CloudinaryDotNet.Actions;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Swashbuckle.AspNetCore.Annotations;
using System.Security.Claims;
using Trackt.DTO;
using Trackt.Models;

namespace Trackt.Controllers
{
    [Authorize] // This attribute ensures that only authenticated users can access these endpoints
    [Route("trips")]
    [ApiController]
    public class TripController : ControllerBase
    {
        private readonly IConfiguration? _configuration;
        private readonly ApplicationDbContext? _context;
        private readonly UserManager<TracktUser>? _userManager;

        public TripController(ApplicationDbContext? context, IConfiguration? configuration,
            UserManager<TracktUser>? userManager)
        {
            _context = context;
            _configuration = configuration;
            _userManager = userManager;
        }

        [HttpPost]
        [ResponseCache(Location = ResponseCacheLocation.Any, Duration = 60)]
        [SwaggerOperation(Summary = "Create Trip", Description = "Create a new Trip")]
        [SwaggerResponse(StatusCodes.Status201Created, "Trip created")]
        [SwaggerResponse(StatusCodes.Status401Unauthorized, "Unauthorized")]
        [SwaggerResponse(StatusCodes.Status500InternalServerError, "Internal Server Error")]
        public async Task<ActionResult<StatusResponse>> Create([FromForm] CreateTrip input)
        {
            try
            {
                var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
                var user = await _userManager!.FindByIdAsync(userId!);

                var valid = user != null && ModelState.IsValid;
                if (valid)
                {
                    //get the uploaded image
                    var imgfile = input.Image;
                    //check that it is an image
                    var anImage = imgfile!.ContentType.StartsWith("image");

                    if (anImage)
                    {
                        // Cloudinary
                        var cloudinaryCloudName = _configuration!["CloudinarySettings:CloudName"];
                        var cloudinaryApiKey = _configuration["CloudinarySettings:ApiKey"];
                        var cloudinaryApiSecret = _configuration["CloudinarySettings:ApiSecret"];

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

                        var response = new StatusResponse
                        {
                            Status = true,
                            Message = $"Destination '{input.DestinationName}' has been created."
                        };
                        return response;
                        //return StatusCode(201, $"Destination '{input.DestinationName}' has been created.");
                    }
                    else
                    {
                        var response = new StatusResponse
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
                    var response = new StatusResponse
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
        [ResponseCache(Location = ResponseCacheLocation.Any, Duration = 60)]
        [SwaggerOperation(Summary = "List Trip(s)", Description = "List one or all trip(s) for a User")]
        [SwaggerResponse(StatusCodes.Status200OK, "Successful")]
        [SwaggerResponse(StatusCodes.Status401Unauthorized, "Unauthorized")]
        [SwaggerResponse(StatusCodes.Status500InternalServerError, "Internal Server Error")]
        public async Task<ActionResult<StatusResponse>> Get(int? tripId)
        {
            try
            {
                //userId
                var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
                var hasId = tripId.HasValue;

                if (hasId)
                {
                    //destination by id
                    var destination = _context!.Destinations.Where(u => u.UserId == userId && u.DestinationId == tripId);
                    var valid = destination != null;
                    if (valid)
                    {
                        var response = new StatusResponse
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
                        var response = new StatusResponse
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
                        var response = new StatusResponse
                        {
                            Status = true,
                            Message = StatusCodes.Status404NotFound.ToString(),
                            Data = await destinations!.ToListAsync()
                        };
                        return response;
                    }
                    else
                    {
                        var response = new StatusResponse
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
    }


}