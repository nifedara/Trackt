using Microsoft.AspNetCore.Identity;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace Trackt.Models
{
    [Table("User")]
    public class TracktUser : IdentityUser
    {
        public string? Name { get; set; }

        [JsonIgnore] // This attribute is used to prevent serialization loop
        public ICollection<Destination>? Destinations { get; set; }
    }
}
