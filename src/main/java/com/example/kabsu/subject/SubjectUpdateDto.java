package com.example.kabsu.subject;

import com.example.kabsu.types.SubjectType;
import jakarta.validation.constraints.*;

public record SubjectUpdateDto(

        @Size(min = 2, max = 100, message = "Subject name must be between 2 and 100 characters")
        String name,

        @Size(min = 2, max = 20, message = "Subject code must be between 2 and 20 characters")
        @Pattern(regexp = "^[A-Z0-9-]+$", message = "Subject code must contain only uppercase letters, numbers, and hyphens")
        String code,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @Min(value = 1, message = "Units must be at least 1")
        @Max(value = 12, message = "Units cannot exceed 12")
        Integer units,

        SubjectType type
) {
}
