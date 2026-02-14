package com.example.kabsu.school;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    public SchoolService(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    @Transactional
    public SchoolResponseDto create(final SchoolRequestDto dto) {
        var school = schoolMapper.toEntity(dto);
        var saved = schoolRepository.save(school);
        return schoolMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public SchoolResponseDto find(Long id) {
        var school = schoolRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("School not found with id " + id));
        return schoolMapper.toDto(school);
    }

    @Transactional(readOnly = true)
    public List<SchoolResponseDto> findAll(Pageable pageable) {
        return schoolRepository.findAll(pageable)
                .stream()
                .map(schoolMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SchoolResponseDto> findSchoolByName(String name, Pageable pageable) {
        return schoolRepository.findAllByNameContainsIgnoreCase(name, pageable)
                .stream()
                .map(schoolMapper::toDto)
                .toList();
    }

    @Transactional
    public SchoolResponseDto update(Long id, SchoolUpdateDto dto) {
        var school = schoolRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("School not found with id " + id));

        schoolMapper.updateEntity(school, dto);
        var saved = schoolRepository.save(school);
        return schoolMapper.toDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        var school = schoolRepository.findById(id)
                        .orElseThrow(()-> new EntityNotFoundException("School not found with id " + id));
        schoolRepository.delete(school);
    }

}
