package com.example.kabsu.school;

import com.example.kabsu.school.request.SchoolRequest;
import com.example.kabsu.school.request.SchoolUpdateRequest;
import com.example.kabsu.school.response.SchoolResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SchoolService {
    SchoolResponse create(SchoolRequest request);
    SchoolResponse find(Long id);
    List<SchoolResponse> findAll(Pageable pageable);
    List<SchoolResponse> findSchoolByName(String schoolName, Pageable pageable);
    SchoolResponse update(Long schoolId, SchoolUpdateRequest request);
    void delete(Long schoolId);
}
