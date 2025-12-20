using Microsoft.AspNetCore.Mvc;
using BrasilBurger.CSharp.Models;
using System.Collections.Generic;
using System.Linq;

namespace BrasilBurger.CSharp.Controllers
{
    public class ZoneController : Controller
    {
        private static List<Zone> zones = new List<Zone>(); // Simule la BD

        // GET: /Zone
        public IActionResult Index()
        {
            return View(zones);
        }

        // GET: /Zone/Details/5
        public IActionResult Details(int id)
        {
            var z = zones.FirstOrDefault(zone => zone.Id == id);
            if (z == null) return NotFound();
            return View(z);
        }

        // GET: /Zone/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: /Zone/Create
        [HttpPost]
        public IActionResult Create(Zone zone)
        {
            zone.Id = zones.Count > 0 ? zones.Max(z => z.Id) + 1 : 1;
            zones.Add(zone);
            return RedirectToAction("Index");
        }

        // GET: /Zone/Edit/5
        public IActionResult Edit(int id)
        {
            var z = zones.FirstOrDefault(zone => zone.Id == id);
            if (z == null) return NotFound();
            return View(z);
        }

        // POST: /Zone/Edit/5
        [HttpPost]
        public IActionResult Edit(Zone zone)
        {
            var existing = zones.FirstOrDefault(z => z.Id == zone.Id);
            if (existing == null) return NotFound();

            existing.Nom = zone.Nom;
            existing.PrixLivraison = zone.PrixLivraison;

            return RedirectToAction("Index");
        }
    }
}
