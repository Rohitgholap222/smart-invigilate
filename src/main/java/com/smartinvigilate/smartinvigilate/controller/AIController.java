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
@RequestMapping("/api/v1/ai")
public class AIController {

    @PostMapping("/detect-face")
    public ResponseEntity<Map<String, Object>> detectFace(@RequestParam("image") MultipartFile image) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("faceDetected", true);
        response.put("confidence", 0.98);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/detect-multiple-faces")
    public ResponseEntity<Map<String, Object>> detectMultipleFaces(@RequestParam("image") MultipartFile image) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("facesCount", 1);
        response.put("warning", false);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/eye-tracking")
    public ResponseEntity<Map<String, Object>> eyeTracking(@RequestParam("image") MultipartFile image) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("lookingAtScreen", true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sound-detection")
    public ResponseEntity<Map<String, Object>> soundDetection(@RequestParam("audio") MultipartFile audio) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("noiseLevel", "LOW");
        response.put("speechDetected", false);
        return ResponseEntity.ok(response);
    }
}
