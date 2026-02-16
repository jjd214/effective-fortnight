package com.example.kabsu.subject;

import com.example.kabsu.common.response.ApiResponse;
import com.example.kabsu.subject.impl.SubjectServiceImpl;
import com.example.kabsu.subject.request.SubjectRequestDto;
import com.example.kabsu.subject.request.SubjectUpdateDto;
import com.example.kabsu.subject.response.SubjectResponseDto;
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

    private final SubjectServiceImpl subjectServiceImpl;

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectResponseDto>> create (@Valid @RequestBody SubjectRequestDto dto) {
        var response = subjectServiceImpl.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{subject-id}")
    public ResponseEntity<ApiResponse<SubjectResponseDto>> find(@PathVariable("subject-id") Long id) {
        var response = subjectServiceImpl.find(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SubjectResponseDto>>> findBySubjectName(
            @RequestParam String subjectName,
            @PageableDefault(sort = "id") Pageable pageable) {
        var response = subjectServiceImpl.findBySubjectName(subjectName,pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectResponseDto>>> findAll(
            @PageableDefault(sort = "id") Pageable pageable) {
        var response = subjectServiceImpl.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/{subject-id}")
    public ResponseEntity<ApiResponse<SubjectResponseDto>> update(
            @PathVariable("subject-id") Long subjectId,
            @Valid @RequestBody SubjectUpdateDto dto) {
        var response = subjectServiceImpl.update(subjectId, dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{subject-id}")
    public ResponseEntity<Void> delete(@PathVariable("subject-id") Long id) {
        subjectServiceImpl.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
