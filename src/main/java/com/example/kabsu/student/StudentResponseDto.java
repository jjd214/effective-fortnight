package com.example.kabsu.student;

import com.example.kabsu.types.Gender;

import java.time.LocalDateTime;

public record StudentResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        Integer age,
        Gender gender,
        SchoolSummaryDto school,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {
}
