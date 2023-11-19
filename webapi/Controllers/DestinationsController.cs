using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Security.Claims;
using webapi.Models;

namespace webapi.Controllers
{
    [Authorize] // This attribute ensures that only authenticated users can access these endpoints
    [Route("[controller]")]
    [ApiController]
    public class DestinationsController : ControllerBase
    {
        private readonly ApplicationDbContext? _context;
        private readonly UserManager<TracktUser>? _userManager;

        public DestinationsController(ApplicationDbContext? context,
            UserManager<TracktUser>? userManager)
        {
            _context = context;
            _userManager = userManager;
        }

        [HttpPost]
        [Route("Create")]
        public async Task<ActionResult> Create(Destination destination)
        {
            try
            {
                var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
                var user = await _userManager!.FindByIdAsync(userId!);


                if (user != null && ModelState.IsValid)
                {
                    destination.UserId = userId;
                    destination.User = user;
                    _context?.Destinations.Add(destination);

                    var result = await _context!.SaveChangesAsync();
                    return StatusCode(201, $"Destination '{destination.DestinationName}' has been created.");
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
                        return NotFound();
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