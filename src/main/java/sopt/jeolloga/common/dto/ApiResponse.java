package sopt.jeolloga.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        int code,
        String msg,
        T data
) {
    public static <T> ApiResponse<T> success(T data, String msg) {
        return new ApiResponse(HttpStatus.OK.value(), msg, data);
    }

    public static <T> ApiResponse success(T data) {
        return success(data, "응답 성공");
    }
}
