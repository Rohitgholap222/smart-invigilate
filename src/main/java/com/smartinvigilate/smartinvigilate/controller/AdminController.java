package com.smartinvigilate.smartinvigilate.controller;

import com.smartinvigilate.smartinvigilate.dto.ExamRequest;
import com.smartinvigilate.smartinvigilate.dto.QuestionRequest;
import com.smartinvigilate.smartinvigilate.dto.RegisterRequest;
import com.smartinvigilate.smartinvigilate.model.*;
import com.smartinvigilate.smartinvigilate.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Student Management
    @PostMapping("/students")
    public ResponseEntity<User> createStudent(@RequestBody RegisterRequest request) {
        // re-using register logic or dedicated logic
        return ResponseEntity.ok(adminService.updateStudent(null, request)); // Simple update to use create if id null if logic allowed
    }

    @PostMapping("/students/bulk")
    public ResponseEntity<List<User>> createStudentsBulk(@RequestBody List<RegisterRequest> requests) {
        return ResponseEntity.ok(adminService.addStudentsBulk(requests));
    }

    @GetMapping("/students")
    public ResponseEntity<List<User>> getAllStudents() {
        return ResponseEntity.ok(adminService.getAllStudents());
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<User> getStudentById(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.getStudentById(id));
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<User> updateStudent(@PathVariable Integer id, @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(adminService.updateStudent(id, request));
    }

    @PatchMapping("/students/{id}/status")
    public ResponseEntity<User> patchStudentStatus(@PathVariable Integer id, @RequestParam boolean isActive) {
        return ResponseEntity.ok(adminService.patchStudentStatus(id, isActive));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        adminService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/students/search")
    public ResponseEntity<List<User>> searchStudents(@RequestParam String query) {
        return ResponseEntity.ok(adminService.searchStudents(query));
    }

    // Exam Management
    @PostMapping("/exams")
    public ResponseEntity<Exam> createExam(@RequestBody ExamRequest request) {
        return ResponseEntity.ok(adminService.createExam(request, null)); // Admin info can be extracted from context
    }

    @GetMapping("/exams")
    public ResponseEntity<List<Exam>> getAllExams() {
        return ResponseEntity.ok(adminService.getAllExams());
    }

    @GetMapping("/exams/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.getExamById(id));
    }

    @PutMapping("/exams/{id}")
    public ResponseEntity<Exam> updateExam(@PathVariable Integer id, @RequestBody ExamRequest request) {
        return ResponseEntity.ok(adminService.updateExam(id, request));
    }

    @DeleteMapping("/exams/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Integer id) {
        adminService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/exams/{id}/activate")
    public ResponseEntity<Exam> activateExam(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.activateExam(id));
    }

    @PatchMapping("/exams/{id}/deactivate")
    public ResponseEntity<Exam> deactivateExam(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.deactivateExam(id));
    }

    // Question Management
    @PostMapping("/exams/{examId}/questions")
    public ResponseEntity<List<Question>> addQuestions(@PathVariable Integer examId, @RequestBody List<QuestionRequest> requests) {
        return ResponseEntity.ok(adminService.addQuestions(examId, requests));
    }

    @GetMapping("/exams/{examId}/questions")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable Integer examId) {
        return ResponseEntity.ok(adminService.getQuestionsByExamId(examId));
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Integer id, @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(adminService.updateQuestion(id, request));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Integer id) {
        adminService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    // Monitoring & Results
    @GetMapping("/exams/{id}/ monitor")
    public ResponseEntity<List<ProctoringLog>> monitorExam(@PathVariable Integer id) {
        // Re-implement or use existing
        return ResponseEntity.ok(adminService.getQuestionsByExamId(id).stream().map(q -> new ProctoringLog()).toList()); // Placeholder for monitor
    }

    @GetMapping("/exams/{id}/cheating")
    public ResponseEntity<List<CheatingFlag>> getCheatingHistory(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.getCheatingFlags(id));
    }

    @PatchMapping("/submissions/{id}/flag")
    public ResponseEntity<Void> flagSubmission(@PathVariable Integer id, @RequestParam String reason) {
        adminService.flagSubmission(id, reason);
        return ResponseEntity.ok().build();
    }

    // Dashboard & Analytics
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> body = new HashMap<>();
        body.put("totalStudents", adminService.getAllStudents().size());
        body.put("totalExams", adminService.getAllExams().size());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/analytics/exams")
    public ResponseEntity<List<Map<String, Object>>> getExamAnalytics() {
        return ResponseEntity.ok(List.of(new HashMap<>()));
    }

    @GetMapping("/analytics/students")
    public ResponseEntity<List<Map<String, Object>>> getStudentAnalytics() {
        return ResponseEntity.ok(List.of(new HashMap<>()));
    }
}
