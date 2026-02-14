package com.example.kabsu.subject;

import com.example.kabsu.student.Student;
import com.example.kabsu.types.SubjectType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String code;
    private String description;
    private Integer units;
    @Enumerated(EnumType.STRING)
    private SubjectType type;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "subjects")
    @JsonIgnoreProperties("subjects")
    private Set<Student> students = new HashSet<>();

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

}
