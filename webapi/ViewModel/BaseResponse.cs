namespace webapi.ViewModel
{
    public class BaseResponse
    {
        public bool Status { get; set; }
        public string? Message { get; set; }
        public object? Data { get; set; }
    }
}
