package com.smartinvigilate.smartinvigilate.controller;

import com.smartinvigilate.smartinvigilate.dto.ProctoringLogRequest;
import com.smartinvigilate.smartinvigilate.model.*;
import com.smartinvigilate.smartinvigilate.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // Exam Flow
    @GetMapping("/exams")
    public ResponseEntity<List<Exam>> getAllExams() {
        return ResponseEntity.ok(studentService.getAllExams());
    }

    @GetMapping("/exams/active")
    public ResponseEntity<List<Exam>> getActiveExams() {
        return ResponseEntity.ok(studentService.getActiveExams());
    }

    @GetMapping("/exams/{id}")
    public ResponseEntity<Exam> getExam(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.getExamById(id));
    }

    @PostMapping("/exams/{id}/start")
    public ResponseEntity<Void> startExam(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        studentService.startExam(id, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/exams/{id}/submit")
    public ResponseEntity<Submission> submitExam(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(studentService.submitExam(id, user));
    }

    @GetMapping("/exams/{id}/result")
    public ResponseEntity<Submission> getResult(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        // Find existing result
        return ResponseEntity.ok(studentService.getExamHistory(user).stream()
                .filter(s -> s.getExam().getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Result not found")));
    }

    @GetMapping("/exams/history")
    public ResponseEntity<List<Submission>> getHistory(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(studentService.getExamHistory(user));
    }

    // Answer Handling
    @PostMapping("/exams/{id}/answers")
    public ResponseEntity<Void> saveAnswer(@PathVariable Integer id, @RequestParam Integer questionId, @RequestParam String selectedOption, @AuthenticationPrincipal User user) {
        studentService.saveAnswer(id, questionId, selectedOption, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exams/{id}/answers")
    public ResponseEntity<List<Answer>> getAnswers(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(studentService.getAnswers(id, user));
    }

    @PatchMapping("/exams/{id}/answers")
    public ResponseEntity<Void> patchAnswer(@PathVariable Integer id, @RequestParam Integer questionId, @RequestParam String selectedOption, @AuthenticationPrincipal User user) {
        studentService.saveAnswer(id, questionId, selectedOption, user);
        return ResponseEntity.ok().build();
    }

    // Proctoring Logs
    @PostMapping("/exams/{id}/logs")
    public ResponseEntity<ProctoringLog> addLog(@PathVariable Integer id, @RequestBody ProctoringLogRequest request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(studentService.addLog(id, request, user));
    }

    @GetMapping("/exams/{id}/logs")
    public ResponseEntity<List<ProctoringLog>> getLogs(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(studentService.getLogs(id, user));
    }

    // Webcam / Monitoring (Mock)
    @PostMapping("/webcam/start")
    public ResponseEntity<String> startWebcam() {
        return ResponseEntity.ok("Webcam started");
    }

    @PostMapping("/webcam/stop")
    public ResponseEntity<String> stopWebcam() {
        return ResponseEntity.ok("Webcam stopped");
    }
}
