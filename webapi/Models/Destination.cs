using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace webapi.Models
{
    [Table("Destination")]
    public class Destination
    {
        [Key]
        public int DestinationId { get; set; }
        [Required]
        public string? DestinationName { get; set; }
        [Required]
        public string? ImageUrl { get; set; }
        [Required]
        [Precision(10, 2)]
        public decimal Budget { get; set; }
        [Required]
        public DateTime Date { get; set; }
        public string? UserId { get; set; }

        // Navigation property for the user
        [JsonIgnore]
        public TracktUser? User { get; set; }
    }
}
