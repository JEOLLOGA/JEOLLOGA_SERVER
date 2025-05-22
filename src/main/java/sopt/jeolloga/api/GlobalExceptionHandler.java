package sopt.jeolloga.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sopt.jeolloga.api.exception.BusinessErrorCode;
import sopt.jeolloga.api.exception.BusinessException;
import sopt.jeolloga.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handlerBusinessException(BusinessException ex) {
        BusinessErrorCode businessErrorCode = ex.getBusinessErrorCode();
        return ResponseEntity.status(businessErrorCode.getStatus())
                .body(new ErrorResponse(businessErrorCode.getCode(), businessErrorCode.getMsg()));
    }
}
