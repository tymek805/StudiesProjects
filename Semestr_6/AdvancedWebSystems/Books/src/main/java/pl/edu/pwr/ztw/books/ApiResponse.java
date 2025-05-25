package pl.edu.pwr.ztw.books;

public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    // Domyślny konstruktor
    public ApiResponse() {
    }

    // Konstruktor z parametrami
    public ApiResponse(T data, String status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    // Getter i setter dla data
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // Getter i setter dla status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter i setter dla message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
