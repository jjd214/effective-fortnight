package com.example.kabsu.student;

import com.example.kabsu.school.School;
import com.example.kabsu.types.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;
    @Column(unique = true)
    private String email;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public void changeSchool(School newSchool) {
        if (school != null) {
            school.removeStudent(this);
        }
        if (newSchool != null) {
            newSchool.addStudent(this);
        }
    }
}
