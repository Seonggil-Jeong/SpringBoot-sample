package com.example.spring.exception;

import com.example.spring.exception.result.AccountErrorResult;
import com.example.spring.exception.result.JwtErrorResult;
import com.example.spring.exception.result.PostErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        log.warn("Exception occur: ", exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(
                "INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR"));
    }

    @ExceptionHandler({AccountException.class})
    public ResponseEntity<ErrorResponse> handleAccountException(final AccountException exception) {
        log.warn("AccountException occur: ", exception);

        return this.makeErrorResponseEntity(exception.getErrorResult());
    }

    @ExceptionHandler({PostException.class})
    public ResponseEntity<ErrorResponse> handlePostException(final PostException exception) {
        log.warn("PostException occur: ", exception);

        return this.makeErrorResponseEntity(exception.getErrorResult());
    }


    @ExceptionHandler({JwtException.class})
    public ResponseEntity<ErrorResponse> handleJwtException(final JwtException exception) {
        log.warn("JwtException occur : ", exception);

        return this.makeErrorResponseEntity(exception.getErrorResult());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("Exception occur: ", ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("VALIDATION_FAILED", ex.getAllErrors().get(0).getDefaultMessage()));
    }


    /**
     * ErrorResponse 생성 Method
     *
     * @param errorResult {HttpStatus, message}
     * @return ResponseEntity
     */
    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final AccountErrorResult errorResult) {
        return ResponseEntity.status(errorResult.getStatus())
                .body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final JwtErrorResult errorResult) {
        return ResponseEntity.status(errorResult.getStatus())
                .body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final PostErrorResult errorResult) {
        return ResponseEntity.status(errorResult.getStatus())
                .body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
    }


    @Getter
    @RequiredArgsConstructor
    static class ErrorResponse {
        private final String code;
        private final String message;
        private final LocalDateTime createdAt = LocalDateTime.now();
    }
}
