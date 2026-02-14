package com.example.kabsu.subject;

import com.example.kabsu.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectResponseDto>> create (@Valid @RequestBody SubjectRequestDto dto) {
        var response = subjectService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{subject-id}")
    public ResponseEntity<ApiResponse<SubjectResponseDto>> find(@PathVariable("subject-id") Long id) {
        var response = subjectService.find(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SubjectResponseDto>>> findSubjectByName(
            @RequestParam String name,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        var response = subjectService.findSubjectByName(name,pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectResponseDto>>> findAll(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        var response = subjectService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/{subject-id}")
    public ResponseEntity<ApiResponse<SubjectResponseDto>> update(
            @PathVariable("subject-id") Long subjectId,
            @Valid @RequestBody SubjectUpdateDto dto) {
        var response = subjectService.update(subjectId, dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{subject-id}")
    public ResponseEntity<Void> delete(@PathVariable("subject-id") Long id) {
        subjectService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
