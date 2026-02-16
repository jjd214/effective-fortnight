package com.example.kabsu.school;

import com.example.kabsu.common.BaseEntity;
import com.example.kabsu.student.Student;
import com.example.kabsu.types.SchoolType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class School extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private SchoolType type;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
        student.setSchool(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setSchool(null);
    }
}
