package com.moharayed.supplier_document_platform.controller;

import com.moharayed.supplier_document_platform.service.S3Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "*")
public class FileUploadController {

    private final S3Service s3Service;

    public FileUploadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String key = s3Service.uploadFile(file);

        return Map.of(
                "message", "File uploaded successfully",
                "s3Key", key
        );
    }
}