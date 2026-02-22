package com.example.kabsu.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL) // hides null fields (e.g. fieldErrors on 500s)
public record ApiError(
        int status,
        String error,          // HTTP reason phrase  — "Bad Request", "Not Found"
        String message,        // human-readable summary
        String path,           // request URI  — "/api/users/123"
        List<FieldError> fieldErrors,  // only populated on validation errors
        String traceId,        // MDC trace ID for log correlation (nullable)
        LocalDateTime timestamp
) {
    public record FieldError(
            String field,
            Object rejectedValue,
            String message
    ) {}
}
