using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;

namespace webapi.DTO
{
    public class DestinationDTO
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
