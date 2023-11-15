using Microsoft.AspNetCore.Identity;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace webapi.Models
{
    [Table("User")]
    public class TracktUser : IdentityUser
    {
        // Navigation property for user's destinations
        public ICollection<Destination>? Destinations { get; set; }
        public string? Name { get; internal set; }
    }
}
