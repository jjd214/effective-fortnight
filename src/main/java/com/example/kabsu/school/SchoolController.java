package com.example.kabsu.school;

import com.example.kabsu.common.response.ApiResponse;
import com.example.kabsu.school.impl.SchoolServiceImpl;
import com.example.kabsu.school.request.SchoolRequestDto;
import com.example.kabsu.school.request.SchoolUpdateDto;
import com.example.kabsu.school.response.SchoolResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schools")
public class SchoolController {

    private final SchoolServiceImpl schoolServiceImpl;

    @PostMapping
    public ResponseEntity<ApiResponse<SchoolResponseDto>> create(@Valid @RequestBody SchoolRequestDto dto) {
        var response = schoolServiceImpl.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{school-id}")
    public ResponseEntity<ApiResponse<SchoolResponseDto>> find(@PathVariable("school-id") Long id) {
        var response = schoolServiceImpl.find(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SchoolResponseDto>>> findAll(
            @PageableDefault(sort = "id")Pageable pageable) {
        var response = schoolServiceImpl.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SchoolResponseDto>>> findSchoolByName(
            @RequestParam(name = "schoolName") String name,
            @PageableDefault(sort = "id") Pageable pageable) {
        var response = schoolServiceImpl.findSchoolByName(name, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/{school-id}")
    public ResponseEntity<ApiResponse<SchoolResponseDto>> update(
            @PathVariable("school-id") Long id,
            @Valid @RequestBody SchoolUpdateDto dto) {
        var response = schoolServiceImpl.update(id,dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{school-id}")
    public ResponseEntity<Void> delete(@PathVariable("school-id") Long id) {
        schoolServiceImpl.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
