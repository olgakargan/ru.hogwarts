package com.example.school.repository;

import com.example.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int age1, int age2);

    @Query(value = "select count(*) from students", nativeQuery = true)
    Integer countAllById();

    @Query(value = "select avg(students.age) from students", nativeQuery = true)
    Float averageAgesStudents();

    @Query(value = "select * from students limit :number" +
            " offset (select count(*) from students) - :number", nativeQuery = true)
    List<Student> getLastStudents(@Param("number") Integer number);
}