using System;
using System.ComponentModel.DataAnnotations;

namespace next_door_farm_backend.Models
{
    public class ProductInOrder
    {
        [Key]
        public Guid RefID { get; set; }
        public Guid OrderId { get; set; }
        public Guid ProductId { get; set; }
        public int Amount { get; set; }

        public ProductInOrder()
        {
        }
    }
}
