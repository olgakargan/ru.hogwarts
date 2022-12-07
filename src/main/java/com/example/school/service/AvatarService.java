package com.example.school.service;

import com.example.school.model.Avatar;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar getOrCreateAvatar(Long id);

    Avatar findAvatarById(Long id);

    List<Avatar> getAllAvatars();

    void deleteAvatarById(Long id) throws IOException;

    Avatar findAvatar(Long studentId);

    Avatar getAvatar(Long studentId);
}