using Microsoft.EntityFrameworkCore;

namespace next_door_farm_backend.Models
{
    public class ApplicationContext : DbContext
    {
        public DbSet<Customers> Customerss { get; set; }
        public ApplicationContext(DbContextOptions<ApplicationContext> options)
            : base(options)
        {
            Database.EnsureCreated();   // создаем базу данных при первом обращении
        }
    }
}