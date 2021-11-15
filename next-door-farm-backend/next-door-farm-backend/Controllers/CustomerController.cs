using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.JsonWebTokens;
using next_door_farm_backend.Models;

namespace next_door_farm_backend.Controllers
{
    public class ProductDto
    {
        public long id { get; set; }
        public string name { get; set; }
        public string description { get; set; }
        public int pricePerKg { get; set; }
        public string imageLink { get; set; }
        public int amount { get; set; }
    }
    
    public class OrderDto
    {
        public long id { get; set; }
        public List<ProductDto> products { get; set; }
        public string status { get; set; }
    }
    
    public class CustomerAndOrdersDto
    {
        public long id { get; set; }
        public string firstName { get; set; }
        public string lastName { get; set; }
        public string address { get; set; }
        public string username { get; set; }
        public string email { get; set; }
        public string phone { get; set; }
        public List<OrderDto> orders { get; set; }

        public CustomerAndOrdersDto(Customers customers, List<OrderDto> orderDto)
        {
            this.id = customers.id;
            this.firstName = customers.firstName;
            this.lastName = customers.lastName;
            this.address = customers.address;
            this.username = customers.username;
            this.email = customers.email;
            this.phone = customers.phone;
            this.orders = orderDto.ToList();
        }
        
    }
    
    public class CustomerController : Controller
    {
        private ApplicationContext db;
        
        public CustomerController(ApplicationContext context)
        {
            db = context;
        }
        
        // GET
        public IActionResult Index()
        {
            return Content("Customer Controller");
        }

        [Route("customers")]
        [HttpGet]
        [Authorize]
        public IActionResult customers()
        {
            long idFromJWT = long.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);
            var customer = db.Customerss.SingleOrDefault(c => c.id == idFromJWT);
            var customerAndOrdersDto = new CustomerAndOrdersDto(customer, new List<OrderDto>());
            return Ok(customerAndOrdersDto);
            //return Ok(db.Customerss.ToList());
        }
    }
}