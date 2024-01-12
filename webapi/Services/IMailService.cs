using webapi.DTO;

namespace webapi.Services
{
    public interface IMailService
    {
        Task SendEmail(MailRequest mailRequest);
    }
}
