package com.example.kabsu.student;

import com.example.kabsu.common.response.ApiResponse;
import com.example.kabsu.student.request.StudentRequest;
import com.example.kabsu.student.request.StudentUpdateRequest;
import com.example.kabsu.student.response.StudentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> create(@Valid @RequestBody StudentRequest request) {
        var response = studentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{student-id}")
    public ResponseEntity<ApiResponse<StudentResponse>> find(@PathVariable("student-id") Long id) {
        var response = studentService.find(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> findAll(
            @PageableDefault(sort = "id") Pageable pageable) {
        var response = studentService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> findStudentByName(
            @RequestParam(name = "firstName") String firstName,
            @PageableDefault(sort = "id") Pageable pageable) {
        var response = studentService.findStudentByFirstName(firstName,pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/{student-id}")
    public ResponseEntity<ApiResponse<StudentResponse>> update(
            @PathVariable("student-id") Long studentId,
            @Valid @RequestBody StudentUpdateRequest request) {
        var response = studentService.update(studentId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{student-id}")
    public ResponseEntity<Void> delete(@PathVariable("student-id") Long id) {
        studentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/{student-id}/subjects/{subject-id}")
    public ResponseEntity<ApiResponse<StudentResponse>> assignSubject(
            @PathVariable("student-id") Long studentId,
            @PathVariable("subject-id") Long subjectId) {
        var response = studentService.assignSubject(studentId, subjectId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{student-id}/subjects/{subject-id}")
    public ResponseEntity<Void> removeSubject(
            @PathVariable("student-id") Long studentId,
            @PathVariable("subject-id") Long subjectId) {
        studentService.removeSubject(studentId,subjectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
