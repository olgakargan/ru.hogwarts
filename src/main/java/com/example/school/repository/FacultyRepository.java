package com.example.school.repository;


import com.example.school.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColor(String color);
}