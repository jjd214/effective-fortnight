package com.example.kabsu.student;

import com.example.kabsu.common.BaseEntity;
import com.example.kabsu.school.School;
import com.example.kabsu.subject.Subject;
import com.example.kabsu.types.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Student extends BaseEntity {

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;
    @Column(unique = true)
    private String email;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToMany
    @JoinTable(
        name = "student_subject",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @JsonIgnoreProperties("students")
    private Set<Subject> subjects = new HashSet<>();

    public void changeSchool(School newSchool) {
        if (school != null) {
            school.removeStudent(this);
        }
        if (newSchool != null) {
            newSchool.addStudent(this);
        }
    }

    public void assignSubject(Subject subject) {
        subjects.add(subject);
        subject.getStudents().add(this);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
        subject.getStudents().remove(this);
    }

}
