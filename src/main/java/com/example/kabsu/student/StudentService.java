package com.example.kabsu.student;

import com.example.kabsu.student.request.StudentRequest;
import com.example.kabsu.student.request.StudentUpdateRequest;
import com.example.kabsu.student.response.StudentResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    StudentResponse create(StudentRequest request);
    StudentResponse find(Long studentId);
    List<StudentResponse> findAll(Pageable pageable);
    List<StudentResponse> findStudentByFirstName(String studentFirstName, Pageable pageable);
    StudentResponse update(Long studentId, StudentUpdateRequest request);
    void delete(Long studentId);
    StudentResponse assignSubject(Long studentId, Long subjectId);
    void removeSubject(Long studentId, Long subjectId);
}
