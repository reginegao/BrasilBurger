using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using System;
using System.IO;
using System.Threading.Tasks;

namespace BrasilBurger.CSharp.Services.Image
{
    public class LocalImageService : IImageService
    {
        private readonly string _imagesPath;
        private readonly IWebHostEnvironment _env;

        public LocalImageService(IConfiguration config, IWebHostEnvironment env)
        {
            _env = env;
            var path = config["Images:Path"] ?? "wwwroot/images";
            // Path is relative to project root
            _imagesPath = Path.Combine(_env.ContentRootPath, path.Replace('/', Path.DirectorySeparatorChar));
            if (!Directory.Exists(_imagesPath)) Directory.CreateDirectory(_imagesPath);
        }

        public async Task<string?> SaveImageAsync(IFormFile file)
        {
            if (file == null || file.Length == 0) return null;

            var ext = Path.GetExtension(file.FileName);
            var fileName = $"img_{DateTime.UtcNow:yyyyMMdd_HHmmss}_{Guid.NewGuid():N}{ext}";
            var fullPath = Path.Combine(_imagesPath, fileName);

            try
            {
                await using var stream = new FileStream(fullPath, FileMode.Create);
                await file.CopyToAsync(stream);
                // Return relative path to use in views: /images/{fileName}
                var relative = Path.Combine("/", Path.GetRelativePath(_env.WebRootPath, fullPath)).Replace("\\", "/");
                return relative;
            }
            catch
            {
                return null;
            }
        }
    }
}
