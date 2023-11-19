using Microsoft.AspNetCore.Identity;
using System.ComponentModel.DataAnnotations.Schema;

namespace webapi.Models
{
    [Table("User")]
    public class TracktUser : IdentityUser
    {
        public string? Name { get; set; }

        // Navigation property for user's destinations
        public ICollection<Destination>? Destinations { get; set; }
    }
}
