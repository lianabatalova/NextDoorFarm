using Microsoft.AspNetCore.Mvc;

namespace next_door_farm_backend.Controllers
{
    public class AuthController : Controller
    {
        // GET
        public IActionResult Index()
        {
            return Content("Auth Controller");
        }
    }
}