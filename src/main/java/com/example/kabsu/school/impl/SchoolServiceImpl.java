package com.example.kabsu.school.impl;

import com.example.kabsu.school.*;
import com.example.kabsu.school.request.SchoolRequestDto;
import com.example.kabsu.school.request.SchoolUpdateDto;
import com.example.kabsu.school.response.SchoolResponseDto;
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
    public SchoolResponseDto create(final SchoolRequestDto dto) {
        var school = schoolMapper.toEntity(dto);
        var saved = schoolRepository.save(school);
        return schoolMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public SchoolResponseDto find(final Long id) {
        var school = schoolRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("School not found with id " + id));
        return schoolMapper.toDto(school);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SchoolResponseDto> findAll(Pageable pageable) {
        return schoolRepository.findAll(pageable)
                .stream()
                .map(schoolMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<SchoolResponseDto> findSchoolByName(final String schoolName, Pageable pageable) {
        return schoolRepository.findAllByNameContainsIgnoreCase(schoolName, pageable)
                .stream()
                .map(schoolMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public SchoolResponseDto update(final Long schoolId, final SchoolUpdateDto dto) {
        var school = schoolRepository.findById(schoolId)
                .orElseThrow(()-> new EntityNotFoundException("School not found with id " + schoolId));

        schoolMapper.updateEntity(school, dto);
        var saved = schoolRepository.save(school);
        return schoolMapper.toDto(saved);
    }

    @Transactional
    @Override
    public void delete(final Long schoolId) {
        var school = schoolRepository.findById(schoolId)
                        .orElseThrow(()-> new EntityNotFoundException("School not found with id " + schoolId));
        schoolRepository.delete(school);
    }
}
