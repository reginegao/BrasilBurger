using Microsoft.AspNetCore.Mvc;
using BrasilBurger.CSharp.Models;
using System.Collections.Generic;
using System.Linq;

namespace BrasilBurger.CSharp.Controllers
{
    public class PaiementController : Controller
    {
        private static List<Paiement> paiements = new List<Paiement>();

        // GET: /Paiement
        public IActionResult Index()
        {
            return View(paiements);
        }

        // GET: /Paiement/Details/5
        public IActionResult Details(int id)
        {
            var p = paiements.FirstOrDefault(paiement => paiement.Id == id);
            if (p == null) return NotFound();
            return View(p);
        }

        // GET: /Paiement/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: /Paiement/Create
        [HttpPost]
        public IActionResult Create(Paiement paiement)
        {
            paiement.Id = paiements.Count > 0 ? paiements.Max(p => p.Id) + 1 : 1;
            paiements.Add(paiement);
            return RedirectToAction("Index");
        }
    }
}
