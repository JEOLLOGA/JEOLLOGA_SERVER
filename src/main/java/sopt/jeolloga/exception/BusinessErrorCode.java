package sopt.jeolloga.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCode {
    // 400 BAD REQUEST

    // 401 Unauthorized
    KAKAO_CLIENT_ERROR(HttpStatus.UNAUTHORIZED, "유효하지 않은 Kakao 토큰입니다."),
    KAKAO_UNAUTHORIZED_REFRESHTOKEN(HttpStatus.UNAUTHORIZED, "RefreshToken 검증 실패"),
    INVALID_SERVER_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT입니다."),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "만료된 JWT입니다."),

    // 404 NOT FOUND
    NOT_FOUND_TEMPLESTAY(HttpStatus.NOT_FOUND, "존재하지 않는 템플스테이 입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."),
    NOT_FOUND_FILTER(HttpStatus.NOT_FOUND, "필터 정보가 존재하지 않습니다."),
    NOT_FOUND_ORGANIZEDNAME(HttpStatus.NOT_FOUND, "템플스테이명이 수정되지 않았습니다."),
    SEARCH_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지않는 검색어입니다.");

    private final HttpStatus status;
    private final int code;
    private final String msg;

    BusinessErrorCode(HttpStatus status, String msg) {
        this.status = status;
        this.code = status.value();
        this.msg = msg;
    }
}
