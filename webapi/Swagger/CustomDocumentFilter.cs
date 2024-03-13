using Swashbuckle.AspNetCore.SwaggerGen;
using Microsoft.OpenApi.Models;

namespace Trackt.Swagger
{
    internal class CustomDocumentFilter: IDocumentFilter
    {
        public void Apply( OpenApiDocument swaggerDoc, DocumentFilterContext context)
        {
            swaggerDoc.Info = new OpenApiInfo
            {
                Version = "v1",
                Title = "Trackt API",
                Description = "### An API to track your travels \n " +
                "![Travel image](https://res.cloudinary.com/dbtngbdgn/image/upload/v1706689562/Panama_2dc45fad-7790-4b19-89ac-96595805d30b.jpg) \n" +

                "\n --- \n" +
                "#### Auth \n" +
                "To use protected action methods, you need to add a **token** to the `Authorization` header. \n"
            };
        }
    }
}
