package com.example.kabsu.student;

import com.example.kabsu.school.School;
import com.example.kabsu.school.SchoolRepository;
import com.example.kabsu.subject.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final SchoolRepository schoolRepository;
    private final SubjectRepository subjectRepository;

    public StudentService(StudentRepository studentRepository,
                          StudentMapper studentMapper,
                          SchoolRepository schoolRepository,
                          SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.schoolRepository = schoolRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public StudentResponseDto create(final StudentRequestDto dto) {
        var school = findSchoolById(dto.schoolId());
        var student = studentMapper.toEntity(dto,school);
        var saved = studentRepository.save(student);
        return studentMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public StudentResponseDto find(Long id) {
        var student = studentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + id));
        return studentMapper.toDto(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDto> findAll() {
        return studentRepository.findAllWithSchool()
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDto> findStudentByFirstName(String firstName) {
        return studentRepository.findAllByFirstNameContainsIgnoreCase(firstName)
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Transactional
    public StudentResponseDto update(Long studentId, StudentUpdateDto dto) {
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
    public void delete(Long id) {
        var student = studentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + id));
        studentRepository.delete(student);
    }

    public StudentResponseDto assignSubject(Long studentId, Long subjectId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with id " + studentId));
        var subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new EntityNotFoundException("Subject not found with id " + subjectId));
        student.assignSubject(subject);
        var saved = studentRepository.save(student);
        return studentMapper.toDto(saved);
    }

    private School findSchoolById(Long schoolId) {
        if (schoolId == null) return null;
        return schoolRepository.findById(schoolId)
                .orElseThrow(()-> new EntityNotFoundException("School not found with id " + schoolId));
    }
}
