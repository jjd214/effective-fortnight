package com.example.kabsu.subject;

import com.example.kabsu.subject.request.SubjectRequest;
import com.example.kabsu.subject.request.SubjectUpdateRequest;
import com.example.kabsu.subject.response.SubjectResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubjectService {
    SubjectResponse create(SubjectRequest request);
    SubjectResponse find(Long subjectId);
    List<SubjectResponse> findAll(Pageable pageable);
    List<SubjectResponse> findBySubjectName(String subjectName, Pageable pageable);
    SubjectResponse update(Long subjectId, SubjectUpdateRequest request);
    void delete(Long subjectId);
}
