package com.example.kabsu.student;

import com.example.kabsu.types.SchoolType;

import java.time.LocalDateTime;

public record SchoolSummaryDto(
        Long id,
        String name,
        String description,
        SchoolType type,
        LocalDateTime createdAt
) {
}
