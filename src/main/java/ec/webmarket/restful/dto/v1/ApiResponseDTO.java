package ec.webmarket.restful.dto.v1;

/**
 * Clase genérica para respuestas de API.
 */
public class ApiResponseDTO<T> {
    private boolean success;
    private T result;
    private String message;

    public ApiResponseDTO(boolean success, T result) {
        this.success = success;
        this.result = result;
        this.message = null;
    }

    public ApiResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.result = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
