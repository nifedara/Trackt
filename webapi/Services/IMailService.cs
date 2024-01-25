using Trackt.DTO;

namespace Trackt.Services
{
    public interface IMailService
    {
        Task SendEmail(MailRequest mailRequest);
    }
}
