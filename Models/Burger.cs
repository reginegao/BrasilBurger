using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.CSharp.Models
{
    public class Burger
    {
        public int Id { get; set; }

        [Required]
        public string Nom { get; set; }

        [Required]
        public double Prix { get; set; }

        public string ImageUrl { get; set; }
    }
}
