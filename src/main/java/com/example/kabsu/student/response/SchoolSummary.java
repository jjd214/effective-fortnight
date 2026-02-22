package com.example.kabsu.student.response;

import com.example.kabsu.types.SchoolType;

import java.time.LocalDateTime;

public record SchoolSummary(
        Long id,
        String name,
        String description,
        SchoolType type,
        LocalDateTime createdAt
) {
}
