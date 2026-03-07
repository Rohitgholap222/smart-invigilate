package com.smartinvigilate.smartinvigilate.service;

import com.smartinvigilate.smartinvigilate.dto.ProctoringLogRequest;
import com.smartinvigilate.smartinvigilate.dto.SubmitExamRequest;
import com.smartinvigilate.smartinvigilate.model.*;
import com.smartinvigilate.smartinvigilate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final SubmissionRepository submissionRepository;
    private final ProctoringLogRepository proctoringLogRepository;

    public List<Exam> getActiveExams() {
        return examRepository.findByIsActiveTrue();
    }

    public void startExam(Integer examId, User student) {
        // Log entry or check if already started
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        
        Optional<Submission> existing = submissionRepository.findByStudentAndExam(student, exam);
        if (existing.isEmpty()) {
            Submission submission = Submission.builder()
                    .student(student)
                    .exam(exam)
                    .submissionTime(null)
                    .score(0.0)
                    .isCheated(false)
                    .build();
            submissionRepository.save(submission);
        }
    }

    public Submission submitExam(Integer examId, SubmitExamRequest request, User student) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        
        Submission submission = submissionRepository.findByStudentAndExam(student, exam)
                .orElseThrow(() -> new RuntimeException("Submission record not found, did you start the exam?"));

        if (submission.getSubmissionTime() != null) {
            throw new RuntimeException("Exam already submitted");
        }

        double score = 0;
        List<Question> questions = exam.getQuestions();
        Map<Integer, String> answers = request.getAnswers();

        for (Question q : questions) {
            String studentAnswer = answers.get(q.getId());
            if (studentAnswer != null && studentAnswer.equalsIgnoreCase(q.getCorrectOption())) {
                score++;
            }
        }

        submission.setScore(score);
        submission.setSubmissionTime(LocalDateTime.now());
        return submissionRepository.save(submission);
    }

    public ProctoringLog addLog(Integer examId, ProctoringLogRequest request, User student) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        
        ProctoringLog log = ProctoringLog.builder()
                .student(student)
                .exam(exam)
                .logType(request.getLogType())
                .timestamp(request.getTimestamp() != null ? request.getTimestamp() : LocalDateTime.now())
                .details(request.getDetails())
                .build();
        return proctoringLogRepository.save(log);
    }

    public Submission getResult(Integer examId, User student) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        return submissionRepository.findByStudentAndExam(student, exam)
                .orElseThrow(() -> new RuntimeException("Result not found"));
    }
}
