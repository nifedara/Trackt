using CloudinaryDotNet;
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
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)] // Use this for PostgreSQL serial
        public int DestinationId { get; set; }

        [Required]
        public string? DestinationName { get; set; }

        [Required]
        public string? ImageUrl { get; set; }

        [Required]
        [Column(TypeName = "numeric(10,2)")]
        public decimal Budget { get; set; }

        [Required]
        public string? Date { get; set; } = DateTime.Now.Date.ToString();

        [Required]
        public string? UserId { get; set; }

        [JsonIgnore] // This attribute is used to prevent serialization loop
        public TracktUser? User { get; set; }
    }
}
