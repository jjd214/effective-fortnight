package com.example.kabsu.school;

import com.example.kabsu.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schools")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SchoolResponseDto>> create(@Valid @RequestBody SchoolRequestDto dto) {
        var response = schoolService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{school-id}")
    public ResponseEntity<ApiResponse<SchoolResponseDto>> find(@PathVariable("school-id") Long id) {
        var response = schoolService.find(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SchoolResponseDto>>> findAll() {
        var response = schoolService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SchoolResponseDto>>> findSchoolByName(@RequestParam(name = "schoolName") String name) {
        var response = schoolService.findSchoolByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/{school-id}")
    public ResponseEntity<ApiResponse<SchoolResponseDto>> update(
            @PathVariable("school-id") Long id,
            @Valid @RequestBody SchoolUpdateDto dto) {
        var response = schoolService.update(id,dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{school-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable("school-id") Long id) {
        schoolService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
