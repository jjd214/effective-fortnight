package com.example.kabsu.school.request;

import com.example.kabsu.types.SchoolType;

public record SchoolUpdateDto(
        String name,
        String description,
        SchoolType type
) {
}
