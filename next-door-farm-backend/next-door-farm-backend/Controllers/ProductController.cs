using Microsoft.AspNetCore.Mvc;

namespace next_door_farm_backend.Controllers
{
    public class ProductController : Controller
    {
        // GET
        public IActionResult Index()
        {
            return Content("Product Controller");
        }
    }
}