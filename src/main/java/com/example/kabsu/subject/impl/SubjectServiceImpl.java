package com.example.kabsu.subject.impl;

import com.example.kabsu.student.Student;
import com.example.kabsu.subject.*;
import com.example.kabsu.subject.request.SubjectRequest;
import com.example.kabsu.subject.request.SubjectUpdateRequest;
import com.example.kabsu.subject.response.SubjectResponse;
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
    public SubjectResponse create(final SubjectRequest request) {
        var subject = subjectMapper.toEntity(request);
        var saved = subjectRepository.save(subject);
        return subjectMapper.toSubjectResponse(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public SubjectResponse find(final Long subjectId) {
        var subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + subjectId));
        return subjectMapper.toSubjectResponse(subject);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubjectResponse> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable)
                .stream()
                .map(subjectMapper::toSubjectResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubjectResponse> findBySubjectName(final String subjectName, Pageable pageable) {
        return subjectRepository.findAllByNameContainsIgnoreCase(subjectName, pageable)
                .stream()
                .map(subjectMapper::toSubjectResponse)
                .toList();
    }

    @Transactional
    @Override
    public SubjectResponse update(final Long subjectId, final SubjectUpdateRequest request) {
        var subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + subjectId));

        subjectMapper.updateEntity(request,subject);
        var saved = subjectRepository.save(subject);
        return subjectMapper.toSubjectResponse(saved);
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
