using System;
using System.ComponentModel.DataAnnotations;

namespace next_door_farm_backend.Models
{
    public class Farmers
    {
        [Key]
        public Guid RefID { get; set; }
        public string FirstName { get; set; }
        public string SecondName { get; set; }
        public string Address { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }
        public string Phone { get; set; }
        public string Description { get; set; }
        public double Rating { get; set; }

        public Farmers()
        {
        }
    }
}
