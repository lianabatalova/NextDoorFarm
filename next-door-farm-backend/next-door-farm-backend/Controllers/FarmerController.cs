using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

using next_door_farm_backend.Data;
using next_door_farm_backend.Models;

using System;
using System.Collections.Generic;
using System.Linq;


namespace next_door_farm_backend.Controllers
{
    public class FarmerController : Controller
    {
        private ApplicationDbContext db;

        public FarmerController(ApplicationDbContext dbContext)
        {
            db = dbContext;
        }

        [Route("farmers")]
        [HttpGet]
        [Authorize]
        public IActionResult GetFarmers()
        {
            Guid idFromJwt = Guid.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);
            return Ok(GetFarmerAndProductsDto(idFromJwt));
        }

        [Route("farmers")]
        [HttpPut]
        [Authorize]
        public IActionResult PutFarmers([FromBody] Farmers farmerDto)
        {
            Guid idFromJwt = Guid.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);
            var farmer = db.Farmers.SingleOrDefault(c => c.RefID == idFromJwt);
            
            farmer.Username = farmerDto.Username ?? farmer.Username;
            farmer.Address = farmerDto.Address ?? farmer.Address;
            farmer.Email = farmerDto.Email ?? farmer.Email;
            farmer.Phone = farmerDto.Phone ?? farmer.Phone;
            farmer.FirstName = farmerDto.FirstName ?? farmer.FirstName;
            farmer.SecondName = farmerDto.SecondName ?? farmer.SecondName;
            farmer.Description = farmerDto.Description ?? farmer.Description;
            farmer.Rating = farmerDto.Rating;

            db.SaveChanges();

            var farmerProducts = db.Products.Where(product => product.FarmerId == idFromJwt).ToList();
            var farmerAndProductsDto = new FarmerAndProductsDto(farmer, farmerProducts);
            return Ok(farmerAndProductsDto);
        }

        [Route("farmers")] 
        [HttpDelete]
        [Authorize]
        public IActionResult FarmersDelete([FromBody] Farmers farmerDto)
        {
            Guid idFromJwt = Guid.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);

            var farmer = db.Farmers.SingleOrDefault(c => c.RefID == idFromJwt);
            var farmerProducts = db.Products.Where(product => product.FarmerId == idFromJwt).ToList();
            var farmerAndProductsDto = new FarmerAndProductsDto(farmer, farmerProducts);

            db.Farmers.Remove(farmer);
            db.SaveChanges();

            return Ok(farmerAndProductsDto);
        }


        FarmerAndProductsDto GetFarmerAndProductsDto(Guid farmerId)
        {
            var farmer = db.Farmers.SingleOrDefault(c => c.RefID == farmerId);
            var farmerProducts = db.Products.Where(product => product.FarmerId == farmerId).ToList();
            var farmerAndProductsDto = new FarmerAndProductsDto(farmer, farmerProducts);
            return farmerAndProductsDto;
        }

        [Route("farmers/getAllProducts")] 
        [HttpGet]
        public IActionResult GetAllProductsOfFarmer(string id = null)
        {
            if (id is null)
            {
                var farmers = db.Farmers.ToList();
                List<FarmerAndProductsDto> farmerAndProductsDtos = new List<FarmerAndProductsDto>();
                foreach (var farmer in farmers)
                {
                    farmerAndProductsDtos.Add(GetFarmerAndProductsDto(farmer.RefID));
                }

                return Ok(farmerAndProductsDtos);
            }
            else
            {
                Guid farmerId = Guid.Parse(id);
                return Ok(GetFarmerAndProductsDto(farmerId));
            }
        }
        
        public class ProductsDto
        {
            public Guid id { get; set; }
            public string name { get; set; }
            public string description { get; set; }
            public double pricePerKg { get; set; }
            public string imageLink { get; set; }
            public int amount { get; set; }

            public ProductsDto(Products product)
            {
                this.id = product.RefID;
                this.name = product.Name;
                this.description = product.Description;
                this.pricePerKg = product.PricePerKg;
                this.imageLink = product.ImageLink;
                this.amount = product.Amount;
            }
        }

        public class FarmerAndProductsDto
        {
            public Guid id { get; set; }
            public string firstName { get; set; }
            public string secondName { get; set; }
            public string address { get; set; }
            public string username { get; set; }
            public string email { get; set; }
            public string phone { get; set; }
            public string description { get; set; }
            public double rating { get; set; }
            public List<ProductsDto> products { get; set; }

            public FarmerAndProductsDto(Farmers farmers, List<Products> products)
            {
                this.id = farmers.RefID;
                this.firstName = farmers.FirstName;
                this.secondName = farmers.SecondName;
                this.address = farmers.Address;
                this.username = farmers.Username;
                this.email = farmers.Email;
                this.phone = farmers.Phone;
                this.description = farmers.Description;
                this.rating = farmers.Rating;
                this.products = new List<ProductsDto>();
                foreach (var product in products)
                {
                    this.products.Add(new ProductsDto(product));
                }
            }
        }
    }
}