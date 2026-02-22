package com.example.kabsu.school.request;

import com.example.kabsu.types.SchoolType;
import jakarta.validation.constraints.NotBlank;

public record SchoolRequest(
        @NotBlank String name,
        String description,
        SchoolType type
){
}
