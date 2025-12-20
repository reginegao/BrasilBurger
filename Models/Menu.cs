using System.Collections.Generic;

namespace BrasilBurger.CSharp.Models
{
    public class Menu
    {
        public int Id { get; set; }
        public string Nom { get; set; }
        public double Prix { get; set; }
        public string ImageUrl { get; set; }

        // Liste des éléments qui composent le menu (ex: burger, boisson, frites)
        public List<string> Elements { get; set; } = new List<string>();
    }
}
