package com.example.school.impl;

import com.example.school.model.Avatar;
import com.example.school.model.Student;
import com.example.school.repository.AvatarRepository;
import com.example.school.repository.FacultyRepository;
import com.example.school.repository.StudentRepository;
import com.example.school.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {
    @Value("${avatars.folder}")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    @Override
    public void uploadAvatar(Long id, MultipartFile avatarFile) throws UnableToUploadFileException {
        Student student;

        student = studentRepository.getById(id);
        Path newPath = getNewPath(avatarFile, student);
        copyFile(avatarFile, newPath);
        fillAndSaveAvatar(avatarFile, student, newPath);
    }

    @Override
    public void deleteAvatarById(Long id) throws IOException {
        Optional<Avatar> avatar = avatarRepository.findById(id);
        avatarRepository.deleteById(id);
        Files.deleteIfExists(Path.of(avatar.get().getFilePath()));

    }

    @Override
    public Avatar getOrCreateAvatar(Long id) {
        return avatarRepository.findById(id).orElse(new Avatar());
    }

    @Override
    public Avatar findAvatarById(Long id) {

        return avatarRepository.getById(id);

    }

    private Path getNewPath(MultipartFile avatarFile, Student student) throws UnableToUploadFileException {
        Path filePath;
        try {
            filePath = Path.of(avatarsDir, student + "." +
                    getExtension(Objects.requireNonNull(avatarFile.getOriginalFilename())));
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new UnableToUploadFileException(e);
        }
        return filePath;
    }

    private void copyFile(MultipartFile avatarFile, Path filePath) throws UnableToUploadFileException {
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(os)
        ) {
            bis.transferTo(bos);
        } catch (Exception e) {
            throw new UnableToUploadFileException(e);
        }
    }

    private void fillAndSaveAvatar(MultipartFile avatarFile, Student student, Path filePath) {
        Avatar avatar = getOrCreateAvatar(student.getId());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatarRepository.save(avatar);

    }

    private @NotNull String getExtension(@NotNull String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public List<Avatar> getAllAvatars() {
        return avatarRepository.findAll();
    }

    public class UnableToUploadFileException extends Throwable {
        public UnableToUploadFileException(Exception e) {
        }
    }
}