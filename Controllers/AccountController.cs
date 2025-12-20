using Microsoft.AspNetCore.Mvc;

namespace BrasilBurger.CSharp.Controllers
{
    public class AccountController : Controller
    {
        // GET: /Account/Logout
        public IActionResult Logout()
        {
            TempData.Clear();
            return RedirectToAction("Index", "Home");
        }
    }
}
