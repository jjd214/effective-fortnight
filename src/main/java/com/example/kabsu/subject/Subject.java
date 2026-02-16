package com.example.kabsu.subject;

import com.example.kabsu.common.BaseEntity;
import com.example.kabsu.student.Student;
import com.example.kabsu.types.SubjectType;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Subject extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String code;
    private String description;
    private Integer units;
    @Enumerated(EnumType.STRING)
    private SubjectType type;

    @ManyToMany(mappedBy = "subjects")
    @JsonIgnoreProperties("subjects")
    private Set<Student> students = new HashSet<>();

}
