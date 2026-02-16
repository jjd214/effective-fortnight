package com.example.kabsu.school.response;

import com.example.kabsu.types.SchoolType;

import java.time.LocalDateTime;

public record SchoolResponseDto(
        Long id,
        String name,
        String description,
        SchoolType type,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {
}
