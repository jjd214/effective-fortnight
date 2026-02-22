package com.example.kabsu.school.impl;

import com.example.kabsu.school.*;
import com.example.kabsu.school.request.SchoolRequest;
import com.example.kabsu.school.request.SchoolUpdateRequest;
import com.example.kabsu.school.response.SchoolResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    @Transactional
    @Override
    public SchoolResponse create(final SchoolRequest request) {
        var school = schoolMapper.toEntity(request);
        var saved = schoolRepository.save(school);
        return schoolMapper.toSchoolResponse(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public SchoolResponse find(final Long id) {
        var school = schoolRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("School not found with id " + id));
        return schoolMapper.toSchoolResponse(school);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SchoolResponse> findAll(Pageable pageable) {
        return schoolRepository.findAll(pageable)
                .stream()
                .map(schoolMapper::toSchoolResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<SchoolResponse> findSchoolByName(final String schoolName, Pageable pageable) {
        return schoolRepository.findAllByNameContainsIgnoreCase(schoolName, pageable)
                .stream()
                .map(schoolMapper::toSchoolResponse)
                .toList();
    }

    @Transactional
    @Override
    public SchoolResponse update(final Long schoolId, final SchoolUpdateRequest request) {
        var school = schoolRepository.findById(schoolId)
                .orElseThrow(()-> new EntityNotFoundException("School not found with id " + schoolId));

        schoolMapper.updateEntity(school, request);
        var saved = schoolRepository.save(school);
        return schoolMapper.toSchoolResponse(saved);
    }

    @Transactional
    @Override
    public void delete(final Long schoolId) {
        var school = schoolRepository.findById(schoolId)
                        .orElseThrow(()-> new EntityNotFoundException("School not found with id " + schoolId));
        schoolRepository.delete(school);
    }
}
