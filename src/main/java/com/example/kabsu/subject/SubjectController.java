package com.example.kabsu.subject;

import com.example.kabsu.common.response.ApiResponse;
import com.example.kabsu.subject.request.SubjectRequest;
import com.example.kabsu.subject.request.SubjectUpdateRequest;
import com.example.kabsu.subject.response.SubjectResponse;
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
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectResponse>> create (@Valid @RequestBody SubjectRequest dto) {
        var response = subjectService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{subject-id}")
    public ResponseEntity<ApiResponse<SubjectResponse>> find(@PathVariable("subject-id") Long id) {
        var response = subjectService.find(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SubjectResponse>>> findBySubjectName(
            @RequestParam String subjectName,
            @PageableDefault(sort = "id") Pageable pageable) {
        var response = subjectService.findBySubjectName(subjectName,pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectResponse>>> findAll(
            @PageableDefault(sort = "id") Pageable pageable) {
        var response = subjectService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/{subject-id}")
    public ResponseEntity<ApiResponse<SubjectResponse>> update(
            @PathVariable("subject-id") Long subjectId,
            @Valid @RequestBody SubjectUpdateRequest dto) {
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
