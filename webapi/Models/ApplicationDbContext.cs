using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace webapi.Models
{
    public class ApplicationDbContext : IdentityDbContext<TracktUser>
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
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

        }
        public DbSet<Destination> Destinations { get; set; }

    }
}
