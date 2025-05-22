package sopt.jeolloga.api.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final BusinessErrorCode businessErrorCode;

    public BusinessException(BusinessErrorCode businessErrorCode) {
        super(businessErrorCode.getMsg());
        this.businessErrorCode = businessErrorCode;
    }
}
