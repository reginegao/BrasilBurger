using Microsoft.AspNetCore.Mvc;
using BrasilBurger.CSharp.Models;
using BrasilBurger.CSharp.Data;
using BrasilBurger.CSharp.Services.Image;
using Microsoft.EntityFrameworkCore;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;

namespace BrasilBurger.CSharp.Controllers
{
    public class BurgerController : Controller
    {
        private readonly AppDbContext _db;
        private readonly IImageService _imageService;

        public BurgerController(AppDbContext db, Services.Image.IImageService imageService)
        {
            _db = db;
            _imageService = imageService;
        }

        // GET: /Burger
        public async Task<IActionResult> Index()
        {
            var burgers = await _db.Burgers.ToListAsync();
            return View(burgers);
        }

        // GET: /Burger/Details/1
        public async Task<IActionResult> Details(int id)
        {
            var burger = await _db.Burgers.FindAsync(id);
            if (burger == null) return NotFound();
            return View(burger);
        }

        // GET: /Burger/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: /Burger/Create
        [HttpPost]
        public async Task<IActionResult> Create(global::BrasilBurger.CSharp.Models.Burger burger, IFormFile? image)
        {
            if (ModelState.IsValid)
            {
                if (image != null)
                {
                    var url = await _imageService.SaveImageAsync(image);
                    if (url != null) burger.ImageUrl = url;
                }

                _db.Burgers.Add(burger);
                await _db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(burger);
        }

        // GET: /Burger/Edit/1
        public async Task<IActionResult> Edit(int id)
        {
            var burger = await _db.Burgers.FindAsync(id);
            if (burger == null) return NotFound();
            return View(burger);
        }

        // POST: /Burger/Edit/1
        [HttpPost]
        public async Task<IActionResult> Edit(global::BrasilBurger.CSharp.Models.Burger burger, IFormFile? image)
        {
            if (ModelState.IsValid)
            {
                var existing = await _db.Burgers.FindAsync(burger.Id);
                if (existing != null)
                {
                    existing.Nom = burger.Nom;
                    existing.Prix = burger.Prix;
                    if (image != null)
                    {
                        var url = await _imageService.SaveImageAsync(image);
                        if (url != null) existing.ImageUrl = url;
                    }
                    await _db.SaveChangesAsync();
                }
                return RedirectToAction("Index");
            }
            return View(burger);
        }

        // POST: /Burger/Delete/1
        [HttpPost]
        public async Task<IActionResult> Delete(int id)
        {
            var burger = await _db.Burgers.FindAsync(id);
            if (burger == null) return NotFound();
            _db.Burgers.Remove(burger);
            await _db.SaveChangesAsync();
            return RedirectToAction("Index");
        }
    }
}
