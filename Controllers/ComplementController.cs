using Microsoft.AspNetCore.Mvc;
using BrasilBurger.CSharp.Models;
using System.Collections.Generic;
using System.Linq;

namespace BrasilBurger.CSharp.Controllers
{
    public class ComplementController : Controller
    {
        private static List<Complement> complements = new List<Complement>(); // Simule la BD

        // GET: /Complement
        public IActionResult Index()
        {
            return View(complements);
        }

        // GET: /Complement/Details/5
        public IActionResult Details(int id)
        {
            var comp = complements.FirstOrDefault(c => c.Id == id);
            if (comp == null) return NotFound();
            return View(comp);
        }

        // GET: /Complement/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: /Complement/Create
        [HttpPost]
        public IActionResult Create(Complement complement)
        {
            complement.Id = complements.Count > 0 ? complements.Max(c => c.Id) + 1 : 1;
            complements.Add(complement);
            return RedirectToAction("Index");
        }

        // GET: /Complement/Edit/5
        public IActionResult Edit(int id)
        {
            var comp = complements.FirstOrDefault(c => c.Id == id);
            if (comp == null) return NotFound();
            return View(comp);
        }

        // POST: /Complement/Edit/5
        [HttpPost]
        public IActionResult Edit(Complement complement)
        {
            var existing = complements.FirstOrDefault(c => c.Id == complement.Id);
            if (existing == null) return NotFound();

            existing.Nom = complement.Nom;
            existing.Prix = complement.Prix;
            existing.ImageUrl = complement.ImageUrl;

            return RedirectToAction("Index");
        }
    }
}
