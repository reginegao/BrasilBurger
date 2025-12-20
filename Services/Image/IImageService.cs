using Microsoft.AspNetCore.Http;
using System.Threading.Tasks;

namespace BrasilBurger.CSharp.Services.Image
{
    public interface IImageService
    {
        // Enregistre l'image et retourne l'URL relative ou null en cas d'échec
        Task<string?> SaveImageAsync(IFormFile file);
    }
}
