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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.school.exception.ApiException.FIRST_AGE_MORE_THAN_SECOND_ERROR;


@Service
@RequiredArgsConstructor
@Log4j2
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final StudentMapper mapper;
    static final String CREATED = " started!";


    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private String message;


    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        logger.debug("Calling method createStudent");
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
    public StudentDto editStudent(StudentDto studentDto) {
        logger.debug("Calling method editStudent");

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
        logger.debug("Calling method addStudentToFaculty (studentDto = {}, facultyId = {})", studentDto, facultyId);

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
    public void printStudentsWithThreads1() {List<Student> students = studentRepository.findAll();
        printTwoStudentsNames(students.get(0), students.get(1));

        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                printTwoStudentsNames(students.get(2), students.get(3));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                printTwoStudentsNames(students.get(5), students.get(6));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread2.start();
    }

    @Override
    public void printStudentsWithThreads2() {
        List<Student> students = studentRepository.findAll();
        printStudent(students.get(0));
        printStudent(students.get(1));

        Thread thread1 = new Thread(() -> {
            printStudent(students.get(3));
            printStudent(students.get(4));
        });
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Thread thread2 = new Thread(() -> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        });
        thread2.start();
    }

    private synchronized void printStudent (Student student){
        System.out.println(student.getName());
    }

    private void printTwoStudentsNames (Student student1, Student student2){
        System.out.println(student1.getName());
        System.out.println(student2.getName());
    }


    @Override

    public void deleteStudentById(long id) {
        logger.debug("Calling method deleteStudentById (studentId = {})", id);

        try {
            studentRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new UnableToDeleteException("Student", "id", id, e);
        }
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto, Faculty faculty) {
        return null;
    }

    @Override
    public StudentDto findStudentById(long id) {
        logger.debug("Calling method findStudentById (studentId = {})", id);

        try {
            Student student = studentRepository.getById(id);
            return mapper.toDto(student);
        } catch (Exception e) {
            throw new NotFoundException("Student", "id", id, e);
        }
    }

    @Override
    public List<StudentDto> getAllStudents() {
        logger.debug("Calling method getAllStudents ");

        return MapperConfiguration.convertList(
                studentRepository.findAll(), mapper::toDto);
    }

    @Override
    public List<StudentDto> findByAge(int age) {
        logger.debug("Calling method findByAge (studentId = {})", age);

        return MapperConfiguration.convertList(
                studentRepository.findByAge(age), mapper::toDto);
    }

    @Override
    public List<StudentDto> findByAgeBetween(int min, int max) {
        logger.debug("Calling method findByAgeBetween (min = {}),max = {})", min, max);

        if (max < min) {
            throw new BadRequestException(FIRST_AGE_MORE_THAN_SECOND_ERROR);
        }
        return MapperConfiguration.convertList(
                studentRepository.findByAgeBetween(min, max), mapper::toDto);
    }

    @Override
    public Integer getStudentsCount() {
        logger.debug("Calling method getStudentsCount");

        return studentRepository.countAllById();
    }

    @Override
    public Float getStudentsAgesAverage() {
        logger.debug("Calling method getStudentsAgesAverage");

        return studentRepository.averageAgesStudents();
    }

    @Override
    public double getStudentsAgesAverage2() {
        logger.debug("Calling method getStudentsAgesAverage2");
        return studentRepository.findAll().stream().mapToInt(Student::getAge).average().orElse(0);
    }

    @Override
    public List<StudentDto> getLastStudents(Integer number) {
        logger.debug("Calling method ggetLastStudents (limit = {})", number);

        return MapperConfiguration.convertList(
                studentRepository.getLastStudents(number), mapper::toDto);
    }

    public List<String> getStudentsBeginWithLetter(Character firstChar) {
        logger.debug("getStudentsBeginWithLetter");

        return studentRepository.findAll().stream().filter(student -> student.getName()
                .startsWith(String.valueOf(firstChar))).map(student -> student.getName()
                .toUpperCase()).sorted().collect(Collectors.toList());



}
}