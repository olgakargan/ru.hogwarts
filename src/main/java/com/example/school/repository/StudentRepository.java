package com.example.school.repository;

import com.example.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int age1, int age2);
    @Query(value = "select count(*) from student", nativeQuery = true)
    Integer countAllById();
    @Query(value = "select avg(student.age) from student", nativeQuery = true)
    Float averageAgesStudents();

    @Query(value = "select * from student limit :number" +
            " offset (select count(*) from student) - :number", nativeQuery = true)
    Collection<Student> getLastFifeStudents(@Param("number") Integer number);
}