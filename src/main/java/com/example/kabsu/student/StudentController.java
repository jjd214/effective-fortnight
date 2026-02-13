package com.example.kabsu.student;

import com.example.kabsu.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDto>> create(@Valid @RequestBody StudentRequestDto dto) {
        var response = studentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{student-id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> find(@PathVariable("student-id") Long id) {
        var response = studentService.find(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> findAll() {
        var response = studentService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> findStudentByName(
            @RequestParam(name = "firstName") String firstName) {
        var response = studentService.findStudentByFirstName(firstName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/{student-id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> update(
            @PathVariable("student-id") Long studentId,
            @Valid @RequestBody StudentUpdateDto dto) {
        var response = studentService.update(studentId,dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{student-id}")
    public ResponseEntity<Void> delete(@PathVariable("student-id") Long id) {
        studentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
