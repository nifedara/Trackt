using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;

namespace Trackt.DTO
{
    public class CreateTrip
    {
        [Required]
        public string? DestinationName { get; set; }
        [Required]
        public IFormFile? Image { get; set; }
        [Required]
        [Precision(10, 2)]
        public decimal Budget { get; set; }
        [Required]
        public string? Date { get; set; }
    }
}
