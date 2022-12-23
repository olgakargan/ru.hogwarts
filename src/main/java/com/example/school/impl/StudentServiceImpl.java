package com.example.school.impl;

import com.example.school.configuration.MapperConfiguration;
import com.example.school.dto.StudentDto;
import com.example.school.dto.StudentMapper;
import com.example.school.exception.*;
import com.example.school.model.Faculty;
import com.example.school.model.Student;
import com.example.school.repository.FacultyRepository;
import com.example.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.school.exception.ApiException.FIRST_AGE_MORE_THAN_SECOND_ERROR;

@Service
@RequiredArgsConstructor
@Log4j2
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final StudentMapper mapper;
    static final String CREATED = " started!";

    @Override
    public StudentDto createStudent(StudentDto studentDto, String message) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        try {
            Faculty faculty = facultyRepository.findFacultyByName(studentDto.getFaculty());
            Student student = mapper.toEntity(studentDto);
            student.setFaculty(faculty);
            return mapper.toDto(studentRepository.save(student));
        } catch (RuntimeException e) {


            throw new UnableToCreateException(e, message);
        }
    }

    @Override
    public StudentDto editStudent(StudentDto studentDto, String message) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        try {
            Faculty faculty = facultyRepository.findFacultyByName(studentDto.getFaculty());
            Student student = mapper.toEntity(studentDto);
            student.setFaculty(faculty);
            return mapper.toDto(studentRepository.save(student));
        } catch (RuntimeException e) {
            throw new UnableToUpdateException(e, message);
        }
    }

    @Override
    public StudentDto addStudentToFaculty(StudentDto studentDto, Long facultyId) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        try {
            Faculty faculty = facultyRepository.getById(facultyId);
            Student student = mapper.toEntity(studentDto);
            student.setFaculty(faculty);
            return mapper.toDto(studentRepository.save(student));
        } catch (RuntimeException e) {
            throw new NotFoundException("Faculty", "id", facultyId, e);
        }
    }

    @Override

    public void deleteStudentById(long id) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        try {
            studentRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new UnableToDeleteException("Student", "id", id, e);
        }
    }

    @Override
    public StudentDto findStudentById(long id) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        try {
            Student student = studentRepository.getById(id);
            return mapper.toDto(student);
        } catch (Exception e) {
            throw new NotFoundException("Student", "id", id, e);
        }
    }

    @Override
    public List<StudentDto> getAllStudents() {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        return MapperConfiguration.convertList(
                studentRepository.findAll(), mapper::toDto);
    }

    @Override
    public List<StudentDto> findByAge(int age) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        return MapperConfiguration.convertList(
                studentRepository.findByAge(age), mapper::toDto);
    }

    @Override
    public List<StudentDto> findByAgeBetween(int min, int max) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        if (max < min) {
            throw new BadRequestException(FIRST_AGE_MORE_THAN_SECOND_ERROR);
        }
        return MapperConfiguration.convertList(
                studentRepository.findByAgeBetween(min, max), mapper::toDto);
    }

    @Override
    public Integer getStudentsCount() {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        return studentRepository.countAllById();
    }

    @Override
    public Float getStudentsAgesAverage() {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        return studentRepository.averageAgesStudents();
    }

    @Override
    public List<StudentDto> getLastStudents(Integer number) {
        log.info(new Object() {
        }.getClass().getEnclosingMethod().getName() + CREATED);

        return MapperConfiguration.convertList(
                studentRepository.getLastStudents(number), mapper::toDto);
    }

}