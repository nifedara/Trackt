using System.ComponentModel.DataAnnotations;

namespace Trackt.DTO
{
    public class CreateAccount
    {
        [Required(ErrorMessage = "{0} is required")]
        public string? Name { get; set; }
        [Required(ErrorMessage = "{0} is required")]
        [EmailAddress]
        public string? Email { get; set; }
        [Required(ErrorMessage = "{0} is required")]
        public string? Password { get; set; }
    }
}
