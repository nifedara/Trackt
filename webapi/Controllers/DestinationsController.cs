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
    [Route("[controller]")]
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
        [Route("Create")]
        public async Task<ActionResult> Create([FromForm] DestinationDTO input)
        {
            try
            {
                var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
                var user = await _userManager!.FindByIdAsync(userId!);


                if (user != null && ModelState.IsValid)
                {
                    // Access Image to get the uploaded file
                    var imgfile = input.Image;

                    //////// Cloudinary
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

                    var result = await _context!.SaveChangesAsync();
                    return StatusCode(201, $"Destination '{input.DestinationName}' has been created.");
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
        [Route("Get")]
        public async Task<ActionResult> Get(int? destinationId)
        {
            try
            {
                if (destinationId.HasValue)
                {
                    //destination by id
                    var destination = await _context!.Destinations.FindAsync(destinationId);
                    if (destination != null)
                        return Ok(destination);
                    else
                        return StatusCode(StatusCodes.Status404NotFound);
                }
                else
                {
                    //all destinations
                    var destinations = await _context!.Destinations.ToListAsync();
                    if (destinations == null || destinations.Count == 0)
                        return NotFound();
                    else
                        return Ok(destinations);

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