using Microsoft.AspNetCore.Mvc;

namespace next_door_farm_backend.Controllers
{
    public class OrderController : Controller
    {
        // GET
        public IActionResult Index()
        {
            return Content("Order Controller");
        }
    }
}