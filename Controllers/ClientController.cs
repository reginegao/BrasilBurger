using Microsoft.AspNetCore.Mvc;
using BrasilBurger.CSharp.Models;
using System.Collections.Generic;
using System.Linq;

namespace BrasilBurger.CSharp.Controllers
{
    public class ClientController : Controller
    {
        // Simuler la base de données
        private static List<User> Users = new List<User>();
        private static List<Commande> Commandes = CommandeController.Commandes;

        // GET: /Client/Login
        public IActionResult Login()
        {
            return View();
        }

        // POST: /Client/Login
        [HttpPost]
        public IActionResult Login(string email, string motDePasse)
        {
            var user = Users.FirstOrDefault(u => u.Email == email && u.MotDePasse == motDePasse);
            if (user != null)
            {
                TempData["UserId"] = user.Id;
                TempData["UserName"] = user.Nom;
                return RedirectToAction("MesCommandes");
            }
            ViewBag.Message = "Email ou mot de passe incorrect.";
            return View();
        }

        // GET: /Client/Register
        public IActionResult Register()
        {
            return View();
        }

        // POST: /Client/Register
        [HttpPost]
        public IActionResult Register(User user)
        {
            if (ModelState.IsValid)
            {
                user.Id = Users.Count + 1;
                Users.Add(user);
                TempData["UserId"] = user.Id;
                TempData["UserName"] = user.Nom;
                return RedirectToAction("MesCommandes");
            }
            return View(user);
        }

        // GET: /Client/MesCommandes
        public IActionResult MesCommandes()
        {
            if (!TempData.ContainsKey("UserId")) return RedirectToAction("Login");

            var userIdObj = TempData["UserId"];
            if (userIdObj == null) return RedirectToAction("Login");

            int userId;
            if (userIdObj is int i)
            {
                userId = i;
            }
            else if (!int.TryParse(userIdObj.ToString(), out userId))
            {
                return RedirectToAction("Login");
            }

            TempData.Keep("UserId");
            TempData.Keep("UserName");

            var commandesClient = Commandes.Where(c => c.ClientId == userId).ToList();
            return View(commandesClient);
        }

        // GET: /Client/Logout
        public IActionResult Logout()
        {
            TempData.Remove("UserId");
            TempData.Remove("UserName");
            return RedirectToAction("Index", "Home");
        }
    }
}
