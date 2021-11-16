using System;
using System.ComponentModel.DataAnnotations;

namespace next_door_farm_backend.Models
{
    public class Products
    {
        [Key]
        public Guid RefID { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public float PricePerKg { get; set; }
        public string ImageLink { get; set; }
        public int Amount { get; set; }
        public Guid FarmerId { get; set; }

        public Products()
        {
        }
    }
}
