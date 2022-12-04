package com.example.school.repository;


import com.example.school.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findFacultiesByColorOrNameIgnoreCase(String color, String name );



}
