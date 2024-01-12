using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace webapi.Models
{
    public class ApplicationDbContext : IdentityDbContext<TracktUser>
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options): base(options)
        {
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
                 
                //modelBuilder.Entity<Destination>()
                //    .HasKey(k => k.UserId);

            modelBuilder.Entity<TracktUser>()
                .HasMany(u => u.Destinations)
                .WithOne(d => d.User)
                .HasForeignKey(d => d.UserId)
                .IsRequired()
                .OnDelete(DeleteBehavior.Cascade);

            // PostgreSQL uses serial for auto-incrementing columns
            modelBuilder.Entity<Destination>()
                .Property(p => p.DestinationId)
                .UseIdentityByDefaultColumn();

            // Configure decimal precision for PostgreSQL
            modelBuilder.Entity<Destination>()
                .Property(p => p.Budget)
                .HasColumnType("numeric(10,2)");

            // Use timestamp with time zone for DateTime in PostgreSQL
            //modelBuilder.Entity<Destination>()
            //    .Property(p => p.Date)
            //    .HasColumnType("timestamp with time zone");

        }
        public DbSet<Destination> Destinations { get; set; }
    }
}
