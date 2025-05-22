package sopt.jeolloga.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 400 BAD REQUEST
    BAD_REQUEST_METHOD(HttpStatus.BAD_REQUEST, "허용되지 않은 HTTP 메서드입니다."),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "요청 본문이 잘못되었습니다."),
    ID_BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 헤더에 사용자 ID가 없습니다."),
    MISSING_PATH_VARIABLE(HttpStatus.BAD_REQUEST, "요청 경로에 필요한 변수가 없습니다."),

    // 404 NOT FOUND
    NOT_FOUND_URL(HttpStatus.NOT_FOUND, "요청한 URL을 찾을 수 없습니다."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final HttpStatus status;
    private final int code;
    private final String msg;

    ErrorCode(HttpStatus status, String msg) {
        this.status = status;
        this.code = status.value();
        this.msg = msg;
    }
}