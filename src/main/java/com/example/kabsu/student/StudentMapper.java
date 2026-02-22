package com.example.kabsu.student;

import com.example.kabsu.school.School;
import com.example.kabsu.student.request.StudentRequest;
import com.example.kabsu.student.request.StudentUpdateRequest;
import com.example.kabsu.student.response.SchoolSummary;
import com.example.kabsu.student.response.StudentResponse;
import com.example.kabsu.student.response.SubjectSummary;
import com.example.kabsu.subject.Subject;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(final StudentRequest request, School school) {
        return Student
                .builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .age(request.age())
                .gender(request.gender())
                .school(school)
                .build();
    }

    public void updateEntity(final StudentUpdateRequest request, Student student) {
        if (request.firstName() != null) student.setFirstName(request.firstName());
        if (request.lastName() != null) student.setLastName(request.lastName());
        if (request.email() != null) student.setEmail(request.email());
        if (request.age() != null) student.setAge(request.age());
        if (request.gender() != null) student.setGender(request.gender());
    }

    public StudentResponse toStudentResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getAge(),
                student.getGender(),
                student.getSubjects() != null ?
                        student.getSubjects()
                                        .stream()
                                        .map(this::toSubjectSummary)
                                        .toList() : null,
                student.getSchool() != null ? toSchoolSummary(student.getSchool()) : null,
                student.getUpdatedAt(),
                student.getCreatedAt()
        );
    }

    private SchoolSummary toSchoolSummary(School school) {
        return new SchoolSummary(
                school.getId(),
                school.getName(),
                school.getDescription(),
                school.getType(),
                school.getCreatedAt()
        );
    }

    private SubjectSummary toSubjectSummary(Subject subject) {
        return new SubjectSummary(
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
