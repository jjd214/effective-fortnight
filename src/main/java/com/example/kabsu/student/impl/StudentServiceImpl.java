package com.example.kabsu.student.impl;

import com.example.kabsu.exception.BusinessException;
import com.example.kabsu.exception.ErrorCode;
import com.example.kabsu.school.School;
import com.example.kabsu.school.SchoolRepository;
import com.example.kabsu.student.*;
import com.example.kabsu.student.request.StudentRequest;
import com.example.kabsu.student.request.StudentUpdateRequest;
import com.example.kabsu.student.response.StudentResponse;
import com.example.kabsu.subject.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final SchoolRepository schoolRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    @Override
    public StudentResponse create(final StudentRequest request) {
        checkEmailExists(request.email());
        var school = findSchoolById(request.schoolId());
        var student = studentMapper.toEntity(request,school);
        var saved = studentRepository.save(student);
        return studentMapper.toStudentResponse(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public StudentResponse find(final Long studentId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + studentId));
        return studentMapper.toStudentResponse(student);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentResponse> findAll(Pageable pageable) {
        return studentRepository.findAllWithRelations(pageable)
                .stream()
                .map(studentMapper::toStudentResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentResponse> findStudentByFirstName(final String studentFirstName, Pageable pageable) {
        return studentRepository.findAllByFirstNameContainsIgnoreCase(studentFirstName, pageable)
                .stream()
                .map(studentMapper::toStudentResponse)
                .toList();
    }

    @Transactional
    @Override
    public StudentResponse update(final Long studentId, final StudentUpdateRequest request) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + studentId));

        studentMapper.updateEntity(request, student);

        if (request.schoolId() != null) {
            var newSchool = findSchoolById(request.schoolId());
            student.changeSchool(newSchool);
        }

        var saved = studentRepository.save(student);
        return studentMapper.toStudentResponse(saved);
    }

    @Transactional
    @Override
    public void delete(Long studentId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + studentId));
        studentRepository.delete(student);
    }

    @Transactional
    @Override
    public StudentResponse assignSubject(final Long studentId, final Long subjectId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + studentId));
        var subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + subjectId));
        student.assignSubject(subject);
        var saved = studentRepository.save(student);
        return studentMapper.toStudentResponse(saved);
    }

    @Transactional
    @Override
    public void removeSubject(final Long studentId, final Long subjectId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + studentId));
        var subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + subjectId));

        student.removeSubject(subject);
        studentRepository.save(student);
    }

    private School findSchoolById(final Long schoolId) {
        if (schoolId == null) return null;
        return schoolRepository.findById(schoolId)
                .orElseThrow(()-> new EntityNotFoundException("School not found with id " + schoolId));
    }

    private void checkEmailExists(String email) {
        final boolean emailExists = studentRepository.existsByEmailIgnoreCase(email);
        if (emailExists)
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
