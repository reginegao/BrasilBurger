using Microsoft.EntityFrameworkCore;
using BrasilBurger.CSharp.Models;

namespace BrasilBurger.CSharp.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
        {
        }

        // Tables de la base de données (types entièrement qualifiés)
        public DbSet<Burger> Burgers { get; set; }
        public DbSet<Menu> Menus { get; set; }
        public DbSet<Complement> Complements { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<Commande> Commandes { get; set; }
        public DbSet<ItemCommande> ItemsCommandes { get; set; }
        public DbSet<Paiement> Paiements { get; set; }
        public DbSet<Zone> Zones { get; set; }
    }
}
