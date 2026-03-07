package com.smartinvigilate.smartinvigilate.service;

import com.smartinvigilate.smartinvigilate.dto.ExamRequest;
import com.smartinvigilate.smartinvigilate.dto.QuestionRequest;
import com.smartinvigilate.smartinvigilate.model.*;
import com.smartinvigilate.smartinvigilate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ProctoringLogRepository proctoringLogRepository;
    private final SubmissionRepository submissionRepository;

    public Exam createExam(ExamRequest request, User admin) {
        Exam exam = Exam.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .durationInMinutes(request.getDurationInMinutes())
                .isActive(false)
                .createdBy(admin)
                .build();
        return examRepository.save(exam);
    }

    public List<Question> addQuestions(Integer examId, List<QuestionRequest> requests) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        
        List<Question> questions = requests.stream().map(req -> Question.builder()
                .content(req.getContent())
                .optionA(req.getOptionA())
                .optionB(req.getOptionB())
                .optionC(req.getOptionC())
                .optionD(req.getOptionD())
                .correctOption(req.getCorrectOption())
                .exam(exam)
                .build()).collect(Collectors.toList());
        
        return questionRepository.saveAll(questions);
    }

    public Exam activateExam(Integer examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        exam.setActive(true);
        return examRepository.save(exam);
    }

    public List<ProctoringLog> monitorExam(Integer examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        return proctoringLogRepository.findByExam(exam);
    }

    public List<Submission> getResults(Integer examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        // This is a simplification; in a real app we might want a custom JPQL query
        return submissionRepository.findAll().stream()
                .filter(s -> s.getExam().getId().equals(examId))
                .collect(Collectors.toList());
    }
    
    public Submission handleCheating(Integer submissionId, boolean isCheated) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        submission.setCheated(isCheated);
        return submissionRepository.save(submission);
    }
}
