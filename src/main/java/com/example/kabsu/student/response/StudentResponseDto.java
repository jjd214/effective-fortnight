package com.example.kabsu.student.response;

import com.example.kabsu.types.Gender;

import java.time.LocalDateTime;
import java.util.List;

public record StudentResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        Integer age,
        Gender gender,
        List<SubjectSummaryDto> subjects,
        SchoolSummaryDto school,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {
}
