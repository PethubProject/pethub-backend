package ium.pethub.controller;

import ium.pethub.util.FilePathResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ium.pethub.FilePathResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


@RequiredArgsConstructor
@Controller
public class FileController {

    private final FilePathResolver filePathResolver;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path imagePath = filePathResolver.resolveImagePath();
        try {
            file.transferTo(new File(imagePath.toAbsolutePath().toString()));
            return new ResponseEntity<>("File uploaded successfully. Filename: " + fileName, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file. Filename: " + fileName, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

