package ec.webmarket.restful.security;


/**
 * Clase genérica para respuestas de API.
 * @param <T> Tipo de dato que se retorna en la respuesta.
 */
public class ApiResponseDTO<T> {
    private boolean success;
    private T result;
    private String message;

    /**
     * Constructor para respuestas exitosas con un resultado.
     * @param success Indica si la operación fue exitosa.
     * @param result Objeto devuelto en la respuesta.
     */
    public ApiResponseDTO(boolean success, T result) {
        this.success = success;
        this.result = result;
        this.message = null;
    }

    /**
     * Constructor para respuestas con mensaje de error.
     * @param success Indica si la operación fue exitosa.
     * @param message Mensaje de error o informativo.
     */
    public ApiResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.result = null;
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
