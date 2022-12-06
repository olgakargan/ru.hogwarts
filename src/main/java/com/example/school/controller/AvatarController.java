package com.example.school.controller;

import com.example.school.impl.AvatarServiceImpl;
import com.example.school.model.Avatar;
import com.example.school.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;


    @DeleteMapping(value = "/{id}/avatar")
    public ResponseEntity<Void> deleteAvatarById(@PathVariable Long id) throws IOException {
        avatarService.deleteAvatarById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatarById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .contentLength(avatar.getData().length)
                .body(avatar.getData());
    }


    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId,
                                               @RequestParam MultipartFile avatar) throws IOException {
        try {
            avatarService.uploadAvatar(studentId, avatar);
        } catch (AvatarServiceImpl.UnableToUploadFileException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/avatars")
    public List<Avatar> getAllAvatars() {
        return avatarService.getAllAvatars();
    }

}
