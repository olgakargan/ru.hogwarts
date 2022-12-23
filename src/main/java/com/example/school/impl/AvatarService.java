package com.example.school.impl;

import com.example.school.model.Avatar;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AvatarService {
    void uploadAvatar(Long id, MultipartFile avatarFile, String message);

    Avatar getOrCreateAvatar(Long id);

    Avatar findAvatarById(Long id);

    void deleteAvatarById(Long id);

    List<Avatar> getAllAvatars(int pageNumber, int pageSize);
}