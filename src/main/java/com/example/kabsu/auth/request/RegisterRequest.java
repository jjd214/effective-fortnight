package com.example.kabsu.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "VALIDATION.REGISTRATION.NAME.NOT_BLANK")
        @Size(min = 2, max = 50)
        String name,

        @NotBlank(message = "VALIDATION.REGISTRATION.EMAIL.NOT_BLANK")
        @Email(message = "VALIDATION.REGISTRATION.EMAIL.INVALID")
        String email,

        @NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.NOT_BLANK")
        @Size(min = 3, message = "VALIDATION.REGISTRATION.PASSWORD.SIZE")
//        @Pattern(
//                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
//                message = "VALIDATION.REGISTRATION.PASSWORD.WEAK"
//        )
        String password
) {
}
