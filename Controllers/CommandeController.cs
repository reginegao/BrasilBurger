using Microsoft.AspNetCore.Mvc;
using BrasilBurger.CSharp.Models;
using System.Collections.Generic;
using System.Linq;

namespace BrasilBurger.CSharp.Controllers
{
    public class CommandeController : Controller
    {
        private static List<Commande> commandes = new List<Commande>();
        public static List<Commande> Commandes => commandes;
        private static List<ItemCommande> items = new List<ItemCommande>();

        // GET: /Commande
        public IActionResult Index()
        {
            return View(commandes);
        }

        // GET: /Commande/Details/5
        public IActionResult Details(int id)
        {
            var cmd = commandes.FirstOrDefault(c => c.Id == id);
            if (cmd == null) return NotFound();
            cmd.Items = items.Where(i => i.CommandeId == id).ToList();
            return View(cmd);
        }

        // GET: /Commande/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: /Commande/Create
        [HttpPost]
        public IActionResult Create(Commande commande)
        {
            commande.Id = commandes.Count > 0 ? commandes.Max(c => c.Id) + 1 : 1;
            commande.Statut = "EN_COURS";
            commandes.Add(commande);

            // Ajouter les items
            foreach(var item in commande.Items)
            {
                item.Id = items.Count > 0 ? items.Max(i => i.Id) + 1 : 1;
                item.CommandeId = commande.Id;
                items.Add(item);
            }

            return RedirectToAction("Index");
        }

        // GET: /Commande/Edit/5
        public IActionResult Edit(int id)
        {
            var cmd = commandes.FirstOrDefault(c => c.Id == id);
            if (cmd == null) return NotFound();
            return View(cmd);
        }

        // GET: /Commande/MesCommandes
        public IActionResult MesCommandes()
        {
            if (!TempData.ContainsKey("UserId")) return RedirectToAction("Login", "Client");

            var userIdObj = TempData["UserId"];
            if (userIdObj == null) return RedirectToAction("Login", "Client");

            int userId;
            if (userIdObj is int i)
            {
                userId = i;
            }
            else if (!int.TryParse(userIdObj.ToString(), out userId))
            {
                return RedirectToAction("Login", "Client");
            }

            TempData.Keep("UserId");
            TempData.Keep("UserName");

            var commandesClient = commandes.Where(c => c.ClientId == userId).ToList();
            return View("~/Views/Client/MesCommandes.cshtml", commandesClient);
        }

        // POST: /Commande/Edit/5
        [HttpPost]
        public IActionResult Edit(Commande commande)
        {
            var existing = commandes.FirstOrDefault(c => c.Id == commande.Id);
            if (existing == null) return NotFound();

            existing.TypeRetrait = commande.TypeRetrait;
            existing.AdresseLivraison = commande.AdresseLivraison;
            existing.ZoneId = commande.ZoneId;
            existing.Total = commande.Total;
            existing.Paye = commande.Paye;
            existing.Statut = commande.Statut;

            return RedirectToAction("Index");
        }
    }
}
