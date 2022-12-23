package com.example.school.impl;

import com.example.school.dto.StudentMapper;
import com.example.school.exception.NotFoundException;
import com.example.school.exception.UnableToCreateException;
import com.example.school.exception.UnableToDeleteException;
import com.example.school.exception.UnableToUploadFileException;
import com.example.school.model.Avatar;
import com.example.school.model.Student;
import com.example.school.repository.AvatarRepository;
import com.example.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.school.impl.StudentServiceImpl.CREATED;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AvatarServiceImpl implements AvatarService {
    @Value("${avatars.folder}")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final StudentMapper mapper;


    @Override
    public void uploadAvatar(Long id, MultipartFile avatarFile, String message) {
        log.info(getCurrentMethodName() + CREATED);

        Student student;
        try {
            student = studentRepository.getById(id);
        } catch (Exception e) {
            throw new NotFoundException("Student", "id", id, e);
        }

        Path newPath = getNewPath(avatarFile, student);
        copyFile(avatarFile, newPath);
        fillAndSaveAvatar(avatarFile, student, newPath, message);
    }

    @Override
    public void deleteAvatarById(Long id) {
        log.info(getCurrentMethodName() + CREATED);

        Optional<Avatar> avatar = avatarRepository.findById(id);
        try {
            avatarRepository.deleteById(id);
            Files.deleteIfExists(Path.of(avatar.get().getFilePath()));
        } catch (RuntimeException | IOException e) {
            throw new UnableToDeleteException("Avatar", "id", id, e);
        }
    }

    @Override
    public Avatar getOrCreateAvatar(Long id) {
        log.info(getCurrentMethodName() + CREATED);

        return avatarRepository.findById(id).orElse(new Avatar());
    }

    @Override
    public Avatar findAvatarById(Long id) {
        log.info(getCurrentMethodName() + CREATED);

        try {
            return avatarRepository.getById(id);
        } catch (Exception e) {
            throw new NotFoundException("Avatar for Student", "id", id, e);
        }
    }

    @Override
    public List<Avatar> getAllAvatars(int pageNumber, int pageSize) {
        log.info(getCurrentMethodName() + CREATED);
        PageRequest pageRequest = PageRequest.of(--pageNumber, pageSize,
                Sort.Direction.ASC, "fileSize");
        return avatarRepository.findAll(pageRequest).getContent();
    }


    private Path getNewPath(MultipartFile avatarFile, Student student) {
        log.info(getCurrentMethodName() + CREATED);

        Path filePath;
        try {
            filePath = Path.of(avatarsDir, mapper.toDto(student) + "." +
                    getExtension(Objects.requireNonNull(avatarFile.getOriginalFilename())));
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new UnableToUploadFileException(e);
        }
        return filePath;
    }

    private void copyFile(MultipartFile avatarFile, Path filePath) {
        log.info(getCurrentMethodName() + CREATED);

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

    private void fillAndSaveAvatar(MultipartFile avatarFile, Student student, Path filePath, String message) {
        log.info(getCurrentMethodName() + CREATED);

        Avatar avatar = getOrCreateAvatar(student.getId());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());

        try {
            avatar.setData(avatarFile.getBytes());
            avatarRepository.save(avatar);
        } catch (Exception e) {
            throw new UnableToCreateException(e, message);
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String getCurrentMethodName() {
        return StackWalker.getInstance()
                .walk(s -> s.skip(1).findFirst())
                .get()
                .getMethodName();
    }

}