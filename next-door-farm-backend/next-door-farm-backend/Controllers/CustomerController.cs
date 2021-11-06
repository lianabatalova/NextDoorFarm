using Microsoft.AspNetCore.Mvc;

namespace next_door_farm_backend.Controllers
{
    public class CustomerController : Controller
    {
        // GET
        public IActionResult Index()
        {
            return Content("Customer Controller");
        }
    }
}