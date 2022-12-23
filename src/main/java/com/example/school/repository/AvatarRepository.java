package com.example.school.repository;


import com.example.school.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    @Modifying
    @Query("delete from Avatar a where a.id = ?1")
    void deleteByIdWithJPQL(Long id);

    Optional<Avatar> findAvatarByStudentId(Long studentId);
}