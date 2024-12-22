package com.mysite.responseentitytoyproject.global.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * API 오류 응답을 위한 공통 클래스
 */
@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp; // 에러 발생 시간
    private final int status;             // HTTP 상태 코드
    private final String error;           // 에러 타입
    private final String message;         // 에러 메시지
    private final String path;            // 요청 경로

    /**
     * ErrorResponse 생성자
     * @param status HTTP 상태
     * @param message 에러 메시지
     * @param path 요청 경로
     */
    public ErrorResponse(HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}
