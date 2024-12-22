package com.mysite.responseentitytoyproject.global.error.exception;

import com.mysite.responseentitytoyproject.global.common.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 전역 예외 처리를 담당하는 클래스
 * Spring MVC 애플리케이션의 모든 컨트롤러에서 발생하는 예외를 일관된 방식으로 처리
 */
@RestControllerAdvice  // 모든 컨트롤러에 대해 전역적으로 예외 처리를 적용
@Slf4j  // Lombok을 사용한 로깅 설정
public class GlobalExceptionHandler {

    /**
     * 사용자를 찾을 수 없을 때 발생하는 예외 처리
     * @param e UserNotFoundException 예외 객체
     * @param request 현재의 HTTP 요청 객체
     * @return 404 상태 코드와 에러 메시지를 포함한 ResponseEntity
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException e, HttpServletRequest request) {

        // 로그 기록 - 에러 추적을 위해 중요
        log.error("사용자를 찾을 수 없음. 요청 URI: {}", request.getRequestURI(), e);

        // 에러 응답 생성
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,  // 404 상태 코드
                e.getMessage(),        // 예외 메시지
                request.getRequestURI()// 에러가 발생한 요청 경로
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    /**
     * 이메일 중복 발생 시 처리하는 예외 핸들러
     * @param e DuplicateEmailException 예외 객체
     * @param request 현재의 HTTP 요청 객체
     * @return 409 상태 코드와 에러 메시지를 포함한 ResponseEntity
     */
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(
            DuplicateEmailException e, HttpServletRequest request) {

        log.error("이메일 중복 발생. 요청 URI: {}", request.getRequestURI(), e);

        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT,   // 409 상태 코드
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    /**
     * 입력값 검증(@Valid) 실패 시 발생하는 예외 처리
     * @param e MethodArgumentNotValidException 예외 객체
     * @param request 현재의 HTTP 요청 객체
     * @return 400 상태 코드와 검증 실패 상세 내용을 포함한 ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        // 모든 필드 에러를 하나의 문자열로 결합
        String errorMessage = e.getBindingResult()
                .getFieldErrors()      // 검증 실패한 필드들의 에러 정보 가져오기
                .stream()
                .map(error -> {
                    // 각 필드별 에러 메시지 생성
                    return String.format(
                            "%s: %s",
                            error.getField(),           // 에러가 발생한 필드명
                            error.getDefaultMessage()   // 검증 애노테이션에 정의된 메시지
                    );
                })
                .collect(Collectors.joining(", ")); // 쉼표로 구분하여 결합

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,  // 400 상태 코드
                errorMessage,
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * 그 외 모든 예외를 처리하는 핸들러
     * 예상치 못한 예외 상황을 처리하는 마지막 보루
     * @param e Exception 예외 객체
     * @param request 현재의 HTTP 요청 객체
     * @return 500 상태 코드와 일반적인 에러 메시지를 포함한 ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception e, HttpServletRequest request) {

        // 예상치 못한 에러는 상세 로깅이 중요
        log.error("예상치 못한 에러 발생. 요청 URI: {}", request.getRequestURI(), e);

        // 클라이언트에게는 자세한 에러 내용을 숨기고 일반적인 메시지 전달
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,  // 500 상태 코드
                "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
