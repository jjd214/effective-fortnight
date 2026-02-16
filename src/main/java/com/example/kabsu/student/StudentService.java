package com.example.kabsu.student;

import com.example.kabsu.student.request.StudentRequestDto;
import com.example.kabsu.student.request.StudentUpdateDto;
import com.example.kabsu.student.response.StudentResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    StudentResponseDto create(StudentRequestDto dto);
    StudentResponseDto find(Long studentId);
    List<StudentResponseDto> findAll(Pageable pageable);
    List<StudentResponseDto> findStudentByFirstName(String studentFirstName, Pageable pageable);
    StudentResponseDto update(Long studentId, StudentUpdateDto dto);
    void delete(Long studentId);
    StudentResponseDto assignSubject(Long studentId, Long subjectId);
    void removeSubject(Long studentId, Long subjectId);
}
