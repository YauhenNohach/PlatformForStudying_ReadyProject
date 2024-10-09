package by.itstep.application.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private T result;
    private boolean error;
    private String errorMessage;

    public ApiResponse(T result, boolean error, String errorMessage) {
        this.result = result;
        this.error = error;
        this.errorMessage = errorMessage;
    }
    public ApiResponse(T result, boolean error) {
        this.result = result;
        this.error = error;
    }
    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(result, false);
    }
    public static <T> ApiResponse<T> error(T result) {
        return new ApiResponse<>(result, true);
    }
    public static <T> ApiResponse<T> error(String errorMessage) {
        return new ApiResponse<>(null, true, errorMessage);
    }
}
