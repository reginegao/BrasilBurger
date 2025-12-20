namespace BrasilBurger.CSharp.Models
{
    public class Paiement
    {
        public int Id { get; set; }
        public int CommandeId { get; set; }
        public double Montant { get; set; }
        public string Methode { get; set; } // WAVE / OM
    }
}
