package com.smartinvigilate.smartinvigilate.controller;

import com.smartinvigilate.smartinvigilate.dto.ExamRequest;
import com.smartinvigilate.smartinvigilate.dto.QuestionRequest;
import com.smartinvigilate.smartinvigilate.dto.RegisterRequest;
import com.smartinvigilate.smartinvigilate.model.*;
import com.smartinvigilate.smartinvigilate.service.AdminService;
import com.smartinvigilate.smartinvigilate.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationService authenticationService;
    private final AdminService adminService;

    @PostMapping("/add-student")
    public ResponseEntity<String> addStudent(
            @RequestBody RegisterRequest request
    ) {
        try {
            authenticationService.addStudent(request);
            return ResponseEntity.ok("Student added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add-students")
    public ResponseEntity<String> addStudents(
            @RequestBody List<RegisterRequest> requests
    ) {
        try {
            authenticationService.addStudents(requests);
            return ResponseEntity.ok("Students added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/exams")
    public ResponseEntity<Exam> createExam(
            @RequestBody ExamRequest request,
            @AuthenticationPrincipal User admin
    ) {
        return ResponseEntity.ok(adminService.createExam(request, admin));
    }

    @PostMapping("/exams/{examId}/questions")
    public ResponseEntity<List<Question>> addQuestions(
            @PathVariable Integer examId,
            @RequestBody List<QuestionRequest> requests
    ) {
        return ResponseEntity.ok(adminService.addQuestions(examId, requests));
    }

    @PatchMapping("/exams/{examId}/activate")
    public ResponseEntity<Exam> activateExam(@PathVariable Integer examId) {
        return ResponseEntity.ok(adminService.activateExam(examId));
    }

    @GetMapping("/exams/{examId}/monitor")
    public ResponseEntity<List<ProctoringLog>> monitorExam(@PathVariable Integer examId) {
        return ResponseEntity.ok(adminService.monitorExam(examId));
    }

    @GetMapping("/exams/{examId}/results")
    public ResponseEntity<List<Submission>> getResults(@PathVariable Integer examId) {
        return ResponseEntity.ok(adminService.getResults(examId));
    }

    @PatchMapping("/submissions/{submissionId}/cheating")
    public ResponseEntity<Submission> handleCheating(
            @PathVariable Integer submissionId,
            @RequestParam boolean isCheated
    ) {
        return ResponseEntity.ok(adminService.handleCheating(submissionId, isCheated));
    }
}
