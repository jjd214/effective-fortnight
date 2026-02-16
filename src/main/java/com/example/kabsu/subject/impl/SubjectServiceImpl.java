package com.example.kabsu.subject.impl;

import com.example.kabsu.student.Student;
import com.example.kabsu.subject.*;
import com.example.kabsu.subject.request.SubjectRequestDto;
import com.example.kabsu.subject.request.SubjectUpdateDto;
import com.example.kabsu.subject.response.SubjectResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Transactional
    @Override
    public SubjectResponseDto create(final SubjectRequestDto dto) {
        var subject = subjectMapper.toEntity(dto);
        var saved = subjectRepository.save(subject);
        return subjectMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public SubjectResponseDto find(final Long subjectId) {
        var subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + subjectId));
        return subjectMapper.toDto(subject);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubjectResponseDto> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable)
                .stream()
                .map(subjectMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubjectResponseDto> findBySubjectName(final String subjectName, Pageable pageable) {
        return subjectRepository.findAllByNameContainsIgnoreCase(subjectName, pageable)
                .stream()
                .map(subjectMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public SubjectResponseDto update(final Long subjectId, final SubjectUpdateDto dto) {
        var subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + subjectId));

        subjectMapper.updateEntity(dto,subject);
        var saved = subjectRepository.save(subject);
        return subjectMapper.toDto(saved);
    }

    @Transactional
    @Override
    public void delete(final Long subjectId) {
        var subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + subjectId));

        for (Student student : subject.getStudents())
            student.getSubjects().remove(subject);

        subject.getStudents().clear();
        subjectRepository.delete(subject);
    }
}
