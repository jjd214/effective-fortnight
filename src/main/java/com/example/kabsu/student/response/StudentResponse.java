package com.example.kabsu.student.response;

import com.example.kabsu.types.Gender;

import java.time.LocalDateTime;
import java.util.List;

public record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Integer age,
        Gender gender,
        List<SubjectSummary> subjects,
        SchoolSummary school,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {
}
