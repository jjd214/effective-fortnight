package com.example.kabsu.student.request;

import com.example.kabsu.types.Gender;
import jakarta.validation.constraints.*;

public record StudentUpdateRequest(
        @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
        @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "First name can only contain letters, spaces, hyphens, and apostrophes")
        String firstName,

        @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
        @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
        String lastName,

        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        String email,

        @Min(value = 5, message = "Age must be at least 5")
        @Max(value = 100, message = "Age must not exceed 100")
        Integer age,

        Gender gender,

        @Positive(message = "School ID must be a positive number")
        Long schoolId
) {
}
