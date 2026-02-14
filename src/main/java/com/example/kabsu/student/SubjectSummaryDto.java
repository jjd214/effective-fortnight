package com.example.kabsu.student;

import com.example.kabsu.types.SubjectType;

import java.time.LocalDateTime;

public record SubjectSummaryDto(
        Long id,
        String name,
        String code,
        String description,
        Integer units,
        SubjectType subjectType,
        LocalDateTime createdAt
) {
}
