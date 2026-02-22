package com.example.kabsu.subject.response;

import com.example.kabsu.types.SubjectType;

import java.time.LocalDateTime;

public record SubjectResponse(
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
