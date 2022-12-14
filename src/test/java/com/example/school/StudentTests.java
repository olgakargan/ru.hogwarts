package com.example.school;

import com.example.school.model.Student;
import com.example.school.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.school.constant.Constant.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentTest {
    private String REQUEST_URI;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository repository;

    private Student dummy;

    @BeforeEach
    void init() {
        REQUEST_URI = LOCAL_HOST + port + "/student";
        dummy = repository.save(createStudent(GOOD_NAME1, GOOD_AGE1));
    }

    @Test
    void whenCreateGoodStudentThanOk() {
        ResponseEntity<Student> responseEntity =
                restTemplate.postForEntity(REQUEST_URI, dummy, Student.class);
        assert responseEntity.getBody() != null : "Body is null!";

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getAge()).isEqualTo(GOOD_AGE1);
        assertThat(responseEntity.getBody().getName()).isEqualTo(GOOD_NAME1);
        repository.deleteById(dummy.getId());
    }

    @Test
    void whenEditGoodStudentThanOk() {
        Student student2 = createStudent(GOOD_NAME2, GOOD_AGE2);
        student2.setId(dummy.getId());

        HttpEntity<Student> entity = new HttpEntity<>(student2);
        ResponseEntity<Student> responseEntity =
                restTemplate.exchange(REQUEST_URI, HttpMethod.PUT, entity, Student.class);
        assert responseEntity.getBody() != null : "Body is null!";

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getAge()).isEqualTo(GOOD_AGE2);
        assertThat(responseEntity.getBody().getName()).isEqualTo(GOOD_NAME2);
        repository.deleteById(dummy.getId());
    }

    @Test
    void whenGetPresentStudentInfoThanOk() {
        ResponseEntity<Student> responseEntity =
                restTemplate.getForEntity(REQUEST_URI + "/{id}", Student.class, dummy.getId());
        assert responseEntity.getBody() != null : "Body is null!";

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getAge()).isEqualTo(GOOD_AGE1);
        assertThat(responseEntity.getBody().getName()).isEqualTo(GOOD_NAME1);
        repository.deleteById(dummy.getId());
    }

    @Test
    void whenGetNotPresentStudentInfoThanStatusCodeNotFound() {
        ResponseEntity<Student> responseEntity =
                restTemplate.getForEntity(REQUEST_URI + "/{id}", Student.class, 0);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        repository.deleteById(dummy.getId());
    }

    @Test
    void whenDeletePresentStudentThanOk() {
        ResponseEntity<Student> responseEntity =
                restTemplate.exchange(REQUEST_URI + "/{id}", HttpMethod.DELETE, null,
                        Student.class, dummy.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void whenDeleteNotPresentStudentThanStatusCodeBadRequest() {
        ResponseEntity<Student> responseEntity =
                restTemplate.exchange(REQUEST_URI + "/{id}", HttpMethod.DELETE, null,
                        Student.class, 0);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        repository.deleteById(dummy.getId());
    }

    @Test
    void whenGetAllStudentsThanContainsNewAddedStudent() {
        ResponseEntity<List<Student>> responseEntity =
                restTemplate.exchange(REQUEST_URI, HttpMethod.GET, null
                        , new ParameterizedTypeReference<>() {
                        });
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Student> students = responseEntity.getBody();
        assert students != null : "Body is null!";

        assertThat(students.get(students.size() - 1).getName()).isEqualTo(GOOD_NAME1);
        assertThat(students.get(students.size() - 1).getAge()).isEqualTo(GOOD_AGE1);
        repository.deleteById(dummy.getId());
    }

    @Test
    void whenFindStudentsByAgeThanContainsKnownStudent() {
        int age = dummy.getAge();
        ResponseEntity<List<Student>> responseEntity =
                restTemplate.exchange(REQUEST_URI + "/age/{age}", HttpMethod.GET, null
                        , new ParameterizedTypeReference<>() {
                        }, age);
        List<Student> students = responseEntity.getBody();
        assert students != null : "Body is null!";

        assertThat(students.get(students.size() - 1).getName()).isEqualTo(GOOD_NAME1);
        students.forEach(student1 -> assertThat(student1.getAge()).isEqualTo(GOOD_AGE1));
        repository.deleteById(dummy.getId());

    }

    @Test
    void whenFindStudentsByAgeBetweenMinAndMaxShouldContainsKnownStudent() {
        ResponseEntity<List<Student>> responseEntity =
                restTemplate.exchange(REQUEST_URI + "/age?min={min}&max={max}", HttpMethod.GET, null
                        , new ParameterizedTypeReference<>() {
                        }, MIN_AGE, MAX_AGE);
        List<Student> students = responseEntity.getBody();
        assert students != null : "Body is null!";

        assertThat(students.get(students.size() - 1).getName()).isEqualTo(GOOD_NAME1);
        students.forEach(student1 -> assertThat(
                student1.getAge() >= MIN_AGE && student1.getAge() <= MAX_AGE));
        repository.deleteById(dummy.getId());
    }

    private Student createStudent(String name, int age) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        return student;
    }
}