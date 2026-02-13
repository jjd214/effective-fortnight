package com.example.kabsu.school;

import com.example.kabsu.types.SchoolType;
import jakarta.validation.constraints.NotBlank;

public record SchoolRequestDto (
        @NotBlank String name,
        String description,
        SchoolType type
){
}
