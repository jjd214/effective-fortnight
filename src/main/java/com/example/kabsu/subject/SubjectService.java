package com.example.kabsu.subject;

import com.example.kabsu.subject.request.SubjectRequestDto;
import com.example.kabsu.subject.request.SubjectUpdateDto;
import com.example.kabsu.subject.response.SubjectResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubjectService {

    SubjectResponseDto create(SubjectRequestDto dto);
    SubjectResponseDto find(Long subjectId);
    List<SubjectResponseDto> findAll(Pageable pageable);
    List<SubjectResponseDto> findBySubjectName(String subjectName, Pageable pageable);
    SubjectResponseDto update(Long subjectId, SubjectUpdateDto dto);
    void delete(Long subjectId);
}
