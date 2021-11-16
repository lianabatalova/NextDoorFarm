using System;
using Microsoft.EntityFrameworkCore;

namespace next_door_farm_backend.Models
{
    [Keyless]
    public class ProductInOrder
    {
        public Guid OrderId { get; set; }
        public Guid ProductId { get; set; }

        public ProductInOrder()
        {
        }
    }
}
