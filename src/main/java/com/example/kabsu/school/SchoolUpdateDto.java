package com.example.kabsu.school;

import com.example.kabsu.types.SchoolType;

import java.util.Optional;

public record SchoolUpdateDto(
        String name,
        String description,
        SchoolType type
) {
}
