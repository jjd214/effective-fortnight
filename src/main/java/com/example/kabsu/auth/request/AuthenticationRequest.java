package com.example.kabsu.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank(message = "VALIDATION.AUTHENTICATION.EMAIL.NOT_BLANK")
        @Email(message = "VALIDATION.AUTHENTICATION.EMAIL.INVALID")
        String email,
        @NotBlank(message = "VALIDATION.AUTHENTICATION.PASSWORD.NOT_BLANK")
        String password
) {
}
