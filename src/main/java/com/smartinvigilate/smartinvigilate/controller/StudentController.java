package com.smartinvigilate.smartinvigilate.controller;

import com.smartinvigilate.smartinvigilate.dto.ProctoringLogRequest;
import com.smartinvigilate.smartinvigilate.dto.SubmitExamRequest;
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

    @GetMapping("/exams/active")
    public ResponseEntity<List<Exam>> getActiveExams() {
        return ResponseEntity.ok(studentService.getActiveExams());
    }

    @PostMapping("/exams/{examId}/start")
    public ResponseEntity<String> startExam(
            @PathVariable Integer examId,
            @AuthenticationPrincipal User student
    ) {
        studentService.startExam(examId, student);
        return ResponseEntity.ok("Exam started");
    }

    @PostMapping("/exams/{examId}/submit")
    public ResponseEntity<Submission> submitExam(
            @PathVariable Integer examId,
            @RequestBody SubmitExamRequest request,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(studentService.submitExam(examId, request, student));
    }

    @PostMapping("/exams/{examId}/logs")
    public ResponseEntity<ProctoringLog> addLog(
            @PathVariable Integer examId,
            @RequestBody ProctoringLogRequest request,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(studentService.addLog(examId, request, student));
    }

    @GetMapping("/exams/{examId}/result")
    public ResponseEntity<Submission> getResult(
            @PathVariable Integer examId,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(studentService.getResult(examId, student));
    }
}
