using System;
using Microsoft.EntityFrameworkCore;
using next_door_farm_backend.Models;

namespace next_door_farm_backend.Data
{
    public class ApplicationDbContext: DbContext
    {
        public DbSet<Customers> Customers { get; set; }
        public DbSet<Farmers> Farmers { get; set; }
        public DbSet<Orders> Orders { get; set; }
        public DbSet<ProductInOrder> ProductInOrder { get; set; }
        public DbSet<Products> Products { get; set; }

        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            :base(options)
        {
            Database.EnsureCreated();
        }
    }
}
