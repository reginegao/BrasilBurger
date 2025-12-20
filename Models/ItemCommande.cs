namespace BrasilBurger.CSharp.Models
{
    public class ItemCommande
    {
        public int Id { get; set; }
        public int CommandeId { get; set; }
        public string TypeProduit { get; set; } // burger / menu / complement
        public int ProduitId { get; set; }
        public int Quantite { get; set; }
        public double PrixUnitaire { get; set; }
    }
}
