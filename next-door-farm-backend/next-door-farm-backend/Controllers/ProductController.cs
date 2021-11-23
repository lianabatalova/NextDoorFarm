using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

using next_door_farm_backend.Data;
using next_door_farm_backend.Models;

using System;
using System.Collections.Generic;
using System.Linq;

namespace next_door_farm_backend.Controllers
{
    public class ProductController : Controller
    {
        private ApplicationDbContext db;
        
        public ProductController(ApplicationDbContext dbContext)
        {
            db = dbContext;
        }
        public class ProductsDto {        
            public Guid RefId { get; set; }
            public string Name { get; set; }
            public string Description { get; set; }
            public double PricePerKg { get; set; }
            public string ImageLink { get; set; }
            public int Amount { get; set; }
            public Guid FarmerId { get; set; }
        }

        [Route("products")]
        [HttpPost]
        [Authorize]
        public IActionResult CreateProducts([FromBody] ProductsDto product)
        {
            Guid idFromJwt = Guid.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);
            var farmer = db.Farmers.SingleOrDefault(c => c.RefID == idFromJwt);
            
            var productInDb = db.Products.FirstOrDefault(p => p.RefID == product.RefId);
            Guid productKey = Guid.NewGuid();
            if (productInDb is null)
            {
                Products newProduct = new Products()
                {
                    RefID = Guid.NewGuid(),
                    Name = product.Name ?? "",
                    Description = product.Description ?? "",
                    PricePerKg = product.PricePerKg,
                    ImageLink = product.ImageLink ?? "",
                    Amount = product.Amount,
                    FarmerId = farmer.RefID
                };
                productKey = newProduct.RefID;
                db.Products.Add(newProduct);
                db.SaveChanges();
            }

            var getProduct = db.Products.SingleOrDefault(p => p.RefID == productKey);
            return Ok(getProduct);
        }

        [Route("products")]
        [HttpPut]
        [Authorize]
        public IActionResult UpdateProducts([FromBody] Products productDto)
        {
            var product = db.Products.SingleOrDefault(p => p.RefID == productDto.RefID);

            product.Name = productDto.Name ?? product.Name;
            product.Description = productDto.Description ?? product.Description;
            product.PricePerKg = productDto.PricePerKg;
            product.ImageLink = productDto.ImageLink ?? product.ImageLink;
            product.Amount = productDto.Amount;

            db.SaveChanges();

            return Ok(product);
        }

        [Route("products")]
        [HttpGet]
        [Authorize]
        public IActionResult GetProducts()
        {
            var allProducts = db.Products.ToList();
            return Ok(allProducts);
        }

        [Route("products/{id}")]
        [HttpGet]
        [Authorize]
        public IActionResult GetProductById([FromBody] Guid productId)
        {
            var product = db.Products.Where(product => productId == product.RefID);
            return Ok(product);
        }
    }
}