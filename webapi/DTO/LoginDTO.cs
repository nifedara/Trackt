using System.ComponentModel.DataAnnotations;

namespace webapi.DTO
{
    public class LoginDTO
    {
        [Required(ErrorMessage = "{0} is required")]
        [EmailAddress]
        public string? Email { get; set; }
        [Required(ErrorMessage = "{0} is required")]
        public string? Password { get; set; }
    }
}
