package com.example.school.impl;

import com.example.school.exception.NotFoundException;
import com.example.school.exception.UnableToCreateException;
import com.example.school.exception.UnableToUploadFileException;
import com.example.school.model.Avatar;
import com.example.school.model.Student;
import com.example.school.repository.AvatarRepository;
import com.example.school.service.AvatarService;
import com.example.school.service.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static com.example.school.exception.ApiException.UNABLE_TO_CREATE;
import static com.example.school.exception.ApiException.UNABLE_TO_UPLOAD;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    @Value("${avatars.folder}")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) {
        Student student = studentService.findStudent(studentId);
        Path newPath = getNewPath(avatarFile, student);
        copyFile(avatarFile, newPath);
        fillAndSaveAvatar(avatarFile, student, newPath);
    }

    @Override
    public Avatar getOrCreateAvatar(Long studentId) {
        return avatarRepository.findAvatarByStudentId(studentId).orElse(new Avatar());
    }

    @Override
    public Avatar getAvatar(Long studentId) {
        return avatarRepository.findAvatarByStudentId(studentId)
                .orElseThrow(() -> new NotFoundException("Avatar for Student", "studentId", studentId));
    }

    @Override
    public List<Avatar> getAvatars(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(--pageNumber, pageSize,
                Sort.Direction.ASC, "fileSize");
        return avatarRepository.findAll(pageRequest).getContent();
    }

    private Path getNewPath(MultipartFile avatarFile, Student student) {
        Path filePath;
        try {
            filePath = Path.of(avatarsDir, student + "." +
                    getExtension(Objects.requireNonNull(avatarFile.getOriginalFilename())));

            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new UnableToUploadFileException(UNABLE_TO_UPLOAD, e);
        }
        return filePath;
    }

    private void copyFile(MultipartFile avatarFile, Path filePath) {
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(os)
        ) {
            bis.transferTo(bos);
        } catch (Exception e) {
            throw new UnableToUploadFileException(UNABLE_TO_UPLOAD, e);
        }
    }

    private void fillAndSaveAvatar(MultipartFile avatarFile, Student student, Path filePath) {
        Avatar avatar = getOrCreateAvatar(student.getId());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());

        try {
            avatar.setData(avatarFile.getBytes());
            avatarRepository.save(avatar);
        } catch (Exception e) {
            throw new UnableToCreateException(UNABLE_TO_CREATE, e);
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


}