package com.example.kabsu.subject;

import com.example.kabsu.student.Student;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    @Transactional
    public SubjectResponseDto create(final SubjectRequestDto dto) {
        var subject = subjectMapper.toEntity(dto);
        var saved = subjectRepository.save(subject);
        return subjectMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public SubjectResponseDto find(Long id) {
        var subject = subjectRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + id));
        return subjectMapper.toDto(subject);
    }

    @Transactional(readOnly = true)
    public List<SubjectResponseDto> findSubjectByName(String name, Pageable pageable) {
        return subjectRepository.findAllByNameContainsIgnoreCase(name, pageable)
                .stream()
                .map(subjectMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubjectResponseDto> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable)
                .stream()
                .map(subjectMapper::toDto)
                .toList();
    }

    @Transactional
    public SubjectResponseDto update(Long id, SubjectUpdateDto dto) {
        var subject = subjectRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + id));

        subjectMapper.updateEntity(dto,subject);
        var saved = subjectRepository.save(subject);
        return subjectMapper.toDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        var subject = subjectRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + id));

        for (Student student : subject.getStudents())
            student.getSubjects().remove(subject);

        subject.getStudents().clear();
        subjectRepository.delete(subject);
    }
}
