using System;
using System.ComponentModel.DataAnnotations;

namespace next_door_farm_backend.Models
{
    public class Orders
    {
        [Key]
        public Guid RefID { get; set; }
        public string Status { get; set; }
        public double Rating { get; set; }
        public Guid CustomerId { get; set; }

        public Orders()
        {
        }
    }
}
