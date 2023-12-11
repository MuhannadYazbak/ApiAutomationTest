package infra;

public class ResponseWrapper<T> {
    private final int statusCode;
    private final T data;
    private final String errorMessage;

    public ResponseWrapper(int statusCode, T data, String errorMessage) {
        this.statusCode = statusCode;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
