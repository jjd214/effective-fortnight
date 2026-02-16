package com.example.kabsu.school;

import com.example.kabsu.school.request.SchoolRequestDto;
import com.example.kabsu.school.request.SchoolUpdateDto;
import com.example.kabsu.school.response.SchoolResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SchoolService {
    SchoolResponseDto create(SchoolRequestDto dto);
    SchoolResponseDto find(Long id);
    List<SchoolResponseDto> findAll(Pageable pageable);
    List<SchoolResponseDto> findSchoolByName(String schoolName, Pageable pageable);
    SchoolResponseDto update(Long schoolId, SchoolUpdateDto dto);
    void delete(Long schoolId);
}
