package com.smartinvigilate.smartinvigilate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/upload")
public class FileUploadController {

    @PostMapping("/profile")
    public ResponseEntity<Map<String, String>> uploadProfile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        response.put("url", "https://mock-storage.com/profiles/" + file.getOriginalFilename());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/exam-material")
    public ResponseEntity<Map<String, String>> uploadExamMaterial(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        response.put("url", "https://mock-storage.com/materials/" + file.getOriginalFilename());
        return ResponseEntity.ok(response);
    }
}
