package com.example.kabsu.school;

import com.example.kabsu.common.response.ApiResponse;
import com.example.kabsu.school.request.SchoolRequest;
import com.example.kabsu.school.request.SchoolUpdateRequest;
import com.example.kabsu.school.response.SchoolResponse;
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

    private final SchoolService schoolService;

    @PostMapping
    public ResponseEntity<ApiResponse<SchoolResponse>> create(@Valid @RequestBody SchoolRequest dto) {
        var response = schoolService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{school-id}")
    public ResponseEntity<ApiResponse<SchoolResponse>> find(@PathVariable("school-id") Long id) {
        var response = schoolService.find(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SchoolResponse>>> findAll(
            @PageableDefault(sort = "id")Pageable pageable) {
        var response = schoolService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SchoolResponse>>> findSchoolByName(
            @RequestParam(name = "schoolName") String name,
            @PageableDefault(sort = "id") Pageable pageable) {
        var response = schoolService.findSchoolByName(name, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/{school-id}")
    public ResponseEntity<ApiResponse<SchoolResponse>> update(
            @PathVariable("school-id") Long id,
            @Valid @RequestBody SchoolUpdateRequest dto) {
        var response = schoolService.update(id,dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{school-id}")
    public ResponseEntity<Void> delete(@PathVariable("school-id") Long id) {
        schoolService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
