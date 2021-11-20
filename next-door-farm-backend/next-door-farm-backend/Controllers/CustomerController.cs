using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.JsonWebTokens;
using next_door_farm_backend.Data;
using next_door_farm_backend.Models;

namespace next_door_farm_backend.Controllers
{
    public class ProductDto
    {
        public Guid id { get; set; }
        public string name { get; set; }
        public string description { get; set; }
        public int pricePerKg { get; set; }
        public string imageLink { get; set; }
        public int amount { get; set; }

        public ProductDto(Products product)
        {
            this.id = product.RefID;
            this.name = product.Name;
            this.description = product.Description;
            this.pricePerKg = (int) product.PricePerKg; // fast fix
            this.imageLink = product.ImageLink;
            this.amount = product.Amount;
        }
    }
    
    public class OrderDto
    {
        public Guid id { get; set; }
        public List<ProductDto> products { get; set; }
        public string status { get; set; }

        public OrderDto(Orders order, ApplicationDbContext db)
        {
            this.id = order.RefID;
            this.status = order.Status;
            this.products = new List<ProductDto>();

            var productIdsObjects = db.ProductInOrder.Where(productInOrder => productInOrder.OrderId == order.RefID).ToList();
            List<Guid> productIds = new List<Guid>();
            foreach (var productIdsObject in productIdsObjects)
            {
                productIds.Add(productIdsObject.ProductId);
            }

            var orderProducts = db.Products.Where(product => productIds.Contains(product.RefID)).ToList();
            
            foreach (var product in orderProducts)
            {
                this.products.Add(new ProductDto(product));
            }
        }
    }
    
    public class CustomerAndOrdersDto
    {
        public Guid id { get; set; }
        public string firstName { get; set; }
        public string lastName { get; set; }
        public string address { get; set; }
        public string username { get; set; }
        public string email { get; set; }
        public string phone { get; set; }
        public List<OrderDto> orders { get; set; }

        public CustomerAndOrdersDto(Customers customers, List<Orders> orders, ApplicationDbContext db)
        {
            this.id = customers.RefID;
            this.firstName = customers.FirstName;
            this.lastName = customers.SecondName;
            this.address = customers.Address;
            this.username = customers.Username;
            this.email = customers.Email;
            this.phone = customers.Phone;
            this.orders = new List<OrderDto>();
            foreach (var order in orders)
            {
                this.orders.Add(new OrderDto(order, db));
            }
        }
        
    }
    
    public class CustomerController : Controller
    {
        private ApplicationDbContext db;
        
        public CustomerController(ApplicationDbContext context)
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
            Guid idFromJwt = Guid.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);
            var customer = db.Customers.SingleOrDefault(c => c.RefID == idFromJwt);
            var customerOrders = db.Orders.Where(order => order.CustomerId == idFromJwt).ToList();
            var customerAndOrdersDto = new CustomerAndOrdersDto(customer, customerOrders, db);
            return Ok(customerAndOrdersDto);
            //return Ok(db.Customerss.ToList());
        }
        
        [Route("customers")]
        [HttpPut]
        [Authorize]
        public IActionResult customers_put([FromBody]Customers customerDto)
        {
            Guid idFromJwt = Guid.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);

            var customer = db.Customers.SingleOrDefault(c => c.RefID == idFromJwt);
            
            customer.Address = customerDto.Address ?? customer.Address;
            customer.Email = customerDto.Email ?? customer.Email; 
            customer.Phone = customerDto.Phone ?? customer.Phone; 
            customer.FirstName = customerDto.FirstName ?? customer.FirstName; 
            customer.SecondName = customerDto.SecondName ?? customer.SecondName;

            db.SaveChanges();
            
            var customerOrders = db.Orders.Where(order => order.CustomerId == idFromJwt).ToList();
            var customerAndOrdersDto = new CustomerAndOrdersDto(customer, customerOrders, db);
            return Ok(customerAndOrdersDto);
        }
        
        [Route("customers")]
        [HttpDelete]
        [Authorize]
        public IActionResult customers_delete([FromBody]Customers customerDto)
        {
            Guid idFromJwt = Guid.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);

            var customer = db.Customers.SingleOrDefault(c => c.RefID == idFromJwt);
            var customerOrders = db.Orders.Where(order => order.CustomerId == idFromJwt).ToList();
            var customerAndOrdersDto = new CustomerAndOrdersDto(customer, customerOrders, db);
            
            db.Customers.Remove(customer);
            db.SaveChanges();

            return Ok(customerAndOrdersDto);
        }
    }
}