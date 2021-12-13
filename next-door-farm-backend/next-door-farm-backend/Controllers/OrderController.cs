using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

using next_door_farm_backend.Data;
using next_door_farm_backend.Models;

using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.EntityFrameworkCore;

namespace next_door_farm_backend.Controllers
{
    public class OrderController : Controller
    {
        private ApplicationDbContext db;

        public OrderController(ApplicationDbContext dbContext)
        {
            db = dbContext;
        }

        [Route("orders")]
        [HttpPut]
        [Authorize]
        public IActionResult addProductToOrder([FromBody]Guid productId)
        {
            Guid idFromJwt = Guid.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);
            var customer = db.Customers.SingleOrDefault(c => c.RefID == idFromJwt);

            var currentOrder = db.Orders.Where(order => order.CustomerId == customer.RefID)
                .SingleOrDefault(order => order.Status == "fillingIn");
            if (currentOrder == null)
            {
                Orders order = new Orders();
                order.RefID = Guid.NewGuid();
                order.Status = "fillingIn";
                order.CustomerId = customer.RefID;
                currentOrder = order;
                db.Orders.Add(order);
            }

            ProductInOrder productInOrder = new ProductInOrder();
            productInOrder.OrderId = currentOrder.RefID;
            productInOrder.ProductId = productId;
            db.ProductInOrder.Add(productInOrder);
            db.SaveChanges();
            OrderDto orderDto = new OrderDto(currentOrder, db);
            return Ok(orderDto);
        }

        [Route("orders")]
        [HttpPut]
        [Authorize]
        public IActionResult DeleteProductFromOrder([FromBody]OrderProductDto orderProductDto)
        {
            Guid idFromJwt = Guid.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);
            var customer = db.Customers.SingleOrDefault(c => c.RefID == idFromJwt);

            var currentOrder = db.Orders.Where(order => order.CustomerId == customer.RefID)
                .SingleOrDefault(order => order.Status == "fillingIn");
            if (currentOrder != null)
            {
                ProductInOrder productInOrder = new ProductInOrder();
                productInOrder.OrderId = currentOrder.RefID;
                productInOrder.ProductId = orderProductDto.productId;
                db.ProductInOrder.Remove(productInOrder);
                db.SaveChanges();
            }
            
            OrderDto orderDto = new OrderDto(currentOrder, db);
            return Ok(orderDto);
        }

        [Route("orders/submit")]
        [HttpPost]
        [Authorize]
        public IActionResult SubmitOrder()
        {
            Guid idFromJwt = Guid.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);
            var customer = db.Customers.SingleOrDefault(c => c.RefID == idFromJwt);
            var currentOrder = db.Orders.Where(order => order.CustomerId == customer.RefID)
                .SingleOrDefault(order => order.Status == "fillingIn");
            if (currentOrder != null)
            {
                currentOrder.Status = "submitted";
                db.SaveChanges();
            }
            OrderDto orderDto = new OrderDto(currentOrder, db);
            return Ok(orderDto);
        }
        
        [Route("orders/cancel")]
        [HttpPost]
        [Authorize]
        public IActionResult CancelOrder()
        {
            Guid idFromJwt = Guid.Parse(HttpContext.User.Claims.First(i => i.Type == "id").Value);
            var customer = db.Customers.SingleOrDefault(c => c.RefID == idFromJwt);
            var currentOrder = db.Orders.Where(order => order.CustomerId == customer.RefID)
                .SingleOrDefault(order => order.Status == "fillingIn");
            if (currentOrder != null)
            {
                currentOrder.Status = "canceled";
                db.SaveChanges();
            }
            OrderDto orderDto = new OrderDto(currentOrder, db);
            return Ok(orderDto);
        }
    }
}

public class OrderProductDto
{
    public Guid productId { get; set; }
    public int amount { get; set; }
}