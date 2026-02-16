package com.example.kabsu.student.impl;

import com.example.kabsu.exception.BusinessException;
import com.example.kabsu.exception.ErrorCode;
import com.example.kabsu.school.School;
import com.example.kabsu.school.SchoolRepository;
import com.example.kabsu.student.*;
import com.example.kabsu.student.request.StudentRequestDto;
import com.example.kabsu.student.request.StudentUpdateDto;
import com.example.kabsu.student.response.StudentResponseDto;
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
    public StudentResponseDto create(final StudentRequestDto dto) {
        checkEmailExists(dto.email());
        var school = findSchoolById(dto.schoolId());
        var student = studentMapper.toEntity(dto,school);
        var saved = studentRepository.save(student);
        return studentMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public StudentResponseDto find(final Long studentId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + studentId));
        return studentMapper.toDto(student);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentResponseDto> findAll(Pageable pageable) {
        return studentRepository.findAllWithRelations(pageable)
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentResponseDto> findStudentByFirstName(final String studentFirstName, Pageable pageable) {
        return studentRepository.findAllByFirstNameContainsIgnoreCase(studentFirstName, pageable)
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public StudentResponseDto update(final Long studentId, final StudentUpdateDto dto) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + studentId));

        studentMapper.updateEntity(dto, student);

        if (dto.schoolId() != null) {
            var newSchool = findSchoolById(dto.schoolId());
            student.changeSchool(newSchool);
        }

        var saved = studentRepository.save(student);
        return studentMapper.toDto(saved);
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
    public StudentResponseDto assignSubject(final Long studentId, final Long subjectId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + studentId));
        var subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + subjectId));
        student.assignSubject(subject);
        var saved = studentRepository.save(student);
        return studentMapper.toDto(saved);
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
