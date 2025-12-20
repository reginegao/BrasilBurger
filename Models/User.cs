using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.CSharp.Models
{
    public class User
    {
        public int Id { get; set; }

        [Required]
        public string Nom { get; set; }

        [Required]
        public string Prenom { get; set; }

        [Required]
        public string Telephone { get; set; }

        [Required]
        [EmailAddress]
        public string Email { get; set; }

        public string MotDePasse { get; set; }

        public string Role { get; set; } = "client"; // client ou admin
    }
}
