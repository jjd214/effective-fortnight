package com.example.kabsu.student;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // KUNIN MO LAHAT NG STUDENT KASAMA YUNG SCHOOL NG SABAY - SABAY
    // @Query("SELECT s FROM Student s LEFT JOIN FETCH s.school")
    @EntityGraph(attributePaths = {"school", "subjects"})
    @Query("SELECT DISTINCT s FROM Student s")
    List<Student> findAllWithRelations(Pageable pageable);
    // @Query("SELECT s FROM Student s LEFT JOIN FETCH s.school WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))")
    @EntityGraph(attributePaths = {"school", "subjects"})
    List<Student> findAllByFirstNameContainsIgnoreCase(String firstName, Pageable pageable);
}
