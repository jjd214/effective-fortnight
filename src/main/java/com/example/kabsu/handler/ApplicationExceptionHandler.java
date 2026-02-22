package com.example.kabsu.handler;

import com.example.kabsu.common.response.ApiError;
import com.example.kabsu.exception.BusinessException;
import com.example.kabsu.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ApiError.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ApiError.FieldError(e.getField(), e.getRejectedValue(), e.getDefaultMessage()))
                .toList();

        return buildError(HttpStatus.BAD_REQUEST, "Validation failed", request, fieldErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<ApiError.FieldError> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(cv -> new ApiError.FieldError(
                        cv.getPropertyPath().toString(),
                        cv.getInvalidValue(),
                        cv.getMessage()))
                .toList();

        return buildError(HttpStatus.BAD_REQUEST, "Constraint violation", request, fieldErrors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(
            BadCredentialsException ex,
            HttpServletRequest request) {

        var errorCode = ErrorCode.BAD_CREDENTIALS;
        return buildError(errorCode.getStatus(), errorCode.getDefaultMessage(), request, null);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request) {

        var errorCode = ex.getErrorCode();
        return buildError(errorCode.getStatus(), ex.getMessage(), request, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected error at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request, null);
    }

// ---- helper ----

    private ResponseEntity<ApiError> buildError(
            HttpStatus status,
            String message,
            HttpServletRequest request,
            List<ApiError.FieldError> fieldErrors) {

        var error = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                fieldErrors,
                null,
//                MDC.get("traceId"),   // null if not using Sleuth / Micrometer Tracing
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(error);
    }
}
