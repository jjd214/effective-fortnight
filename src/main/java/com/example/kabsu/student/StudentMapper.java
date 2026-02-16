package com.example.kabsu.student;

import com.example.kabsu.school.School;
import com.example.kabsu.student.request.StudentRequestDto;
import com.example.kabsu.student.request.StudentUpdateDto;
import com.example.kabsu.student.response.SchoolSummaryDto;
import com.example.kabsu.student.response.StudentResponseDto;
import com.example.kabsu.student.response.SubjectSummaryDto;
import com.example.kabsu.subject.Subject;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(StudentRequestDto dto, School school) {
        return Student
                .builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .age(dto.age())
                .gender(dto.gender())
                .school(school)
                .build();
    }

    public void updateEntity(StudentUpdateDto dto, Student student) {
        if (dto.firstName() != null) student.setFirstName(dto.firstName());
        if (dto.lastName() != null) student.setLastName(dto.lastName());
        if (dto.email() != null) student.setEmail(dto.email());
        if (dto.age() != null) student.setAge(dto.age());
        if (dto.gender() != null) student.setGender(dto.gender());
    }

    public StudentResponseDto toDto(Student student) {
        return new StudentResponseDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getAge(),
                student.getGender(),
                student.getSubjects() != null ?
                        student.getSubjects()
                                        .stream()
                                        .map(this::toSubjectSummaryDto)
                                        .toList() : null,
                student.getSchool() != null ? toSchoolSummaryDto(student.getSchool()) : null,
                student.getUpdatedAt(),
                student.getCreatedAt()
        );
    }

    private SchoolSummaryDto toSchoolSummaryDto(School school) {
        return new SchoolSummaryDto(
                school.getId(),
                school.getName(),
                school.getDescription(),
                school.getType(),
                school.getCreatedAt()
        );
    }

    private SubjectSummaryDto toSubjectSummaryDto(Subject subject) {
        return new SubjectSummaryDto(
                subject.getId(),
                subject.getName(),
                subject.getDescription(),
                subject.getCode(),
                subject.getUnits(),
                subject.getType(),
                subject.getCreatedAt()
        );
    }

}
