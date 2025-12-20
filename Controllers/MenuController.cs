using Microsoft.AspNetCore.Mvc;
using BrasilBurger.CSharp.Models;
using System.Collections.Generic;
using System.Linq;

namespace BrasilBurger.CSharp.Controllers
{
    public class MenuController : Controller
    {
        private static List<Menu> menus = new List<Menu>(); // Simule la BD

        // GET: /Menu
        public IActionResult Index()
        {
            return View(menus);
        }

        // GET: /Menu/Details/5
        public IActionResult Details(int id)
        {
            var menu = menus.FirstOrDefault(m => m.Id == id);
            if (menu == null) return NotFound();
            return View(menu);
        }

        // GET: /Menu/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: /Menu/Create
        [HttpPost]
        public IActionResult Create(Menu menu)
        {
            menu.Id = menus.Count > 0 ? menus.Max(m => m.Id) + 1 : 1;
            menus.Add(menu);
            return RedirectToAction("Index");
        }

        // GET: /Menu/Edit/5
        public IActionResult Edit(int id)
        {
            var menu = menus.FirstOrDefault(m => m.Id == id);
            if (menu == null) return NotFound();
            return View(menu);
        }

        // POST: /Menu/Edit/5
        [HttpPost]
        public IActionResult Edit(Menu menu)
        {
            var existing = menus.FirstOrDefault(m => m.Id == menu.Id);
            if (existing == null) return NotFound();

            existing.Nom = menu.Nom;
            existing.Prix = menu.Prix;
            existing.ImageUrl = menu.ImageUrl;
            existing.Elements = menu.Elements;

            return RedirectToAction("Index");
        }
    }
}
