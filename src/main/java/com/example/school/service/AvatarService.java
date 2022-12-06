package com.example.school.service;

import com.example.school.impl.AvatarServiceImpl;
import com.example.school.model.Avatar;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    void uploadAvatar(Long id, MultipartFile avatarFile) throws AvatarServiceImpl.UnableToUploadFileException;

    Avatar getOrCreateAvatar(Long id);

    Avatar findAvatarById(Long id);

    List<Avatar> getAllAvatars();

    void deleteAvatarById(Long id) throws IOException;
}