package com.example.kabsu.subject;

import com.example.kabsu.types.SubjectType;

import java.time.LocalDateTime;

public record SubjectResponseDto(
        Long id,
        String name,
        String code,
        String description,
        Integer units,
        SubjectType subjectType,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {
}
