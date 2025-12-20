using System;
using System.Collections.Generic;

namespace BrasilBurger.CSharp.Models
{
    public class Commande
    {
        public int Id { get; set; }
        public int ClientId { get; set; }
        public string TypeRetrait { get; set; } // LIVRAISON / RETRAIT
        public string AdresseLivraison { get; set; }
        public int ZoneId { get; set; }
        public double Total { get; set; }
        public bool Paye { get; set; }
        public string Statut { get; set; } // EN_COURS / LIVREE / ANNULEE

        public List<ItemCommande> Items { get; set; } = new List<ItemCommande>();
    }
}
