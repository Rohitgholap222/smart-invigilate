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
    private final AnswerRepository answerRepository;

    public List<Exam> getActiveExams() {
        return examRepository.findByIsActiveTrue();
    }

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(Integer id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
    }

    public void startExam(Integer examId, User user) {
        Exam exam = getExamById(examId);
        Optional<Submission> existing = submissionRepository.findByUserAndExam(user, exam);
        if (existing.isEmpty()) {
            Submission submission = Submission.builder()
                    .user(user)
                    .exam(exam)
                    .submissionTime(null)
                    .score(0.0)
                    .cheatingFlag(false)
                    .build();
            submissionRepository.save(submission);
        }
    }

    public void saveAnswer(Integer examId, Integer questionId, String selectedOption, User user) {
        Exam exam = getExamById(examId);
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        
        Optional<Answer> existing = answerRepository.findByUserIdAndExamIdAndQuestionId(user.getId(), examId, questionId);
        Answer answer = existing.orElse(Answer.builder()
                .user(user)
                .exam(exam)
                .question(question)
                .build());
        
        answer.setSelectedOption(selectedOption);
        answer.setCorrect(selectedOption.equalsIgnoreCase(question.getCorrectOption()));
        answerRepository.save(answer);
    }

    public List<Answer> getAnswers(Integer examId, User user) {
        return answerRepository.findByUserIdAndExamId(user.getId(), examId);
    }

    public Submission submitExam(Integer examId, User user) {
        Exam exam = getExamById(examId);
        Submission submission = submissionRepository.findByUserAndExam(user, exam)
                .orElseThrow(() -> new RuntimeException("Submission record not found, did you start the exam?"));

        if (submission.getSubmissionTime() != null) {
            throw new RuntimeException("Exam already submitted");
        }

        List<Answer> studentAnswers = answerRepository.findByUserIdAndExamId(user.getId(), examId);
        long score = studentAnswers.stream().filter(Answer::isCorrect).count();

        submission.setScore((double) score);
        submission.setSubmissionTime(LocalDateTime.now());
        return submissionRepository.save(submission);
    }

    public List<Submission> getExamHistory(User user) {
        return submissionRepository.findByUser(user);
    }

    public ProctoringLog addLog(Integer examId, ProctoringLogRequest request, User user) {
        Exam exam = getExamById(examId);
        ProctoringLog log = ProctoringLog.builder()
                .user(user)
                .exam(exam)
                .logType(request.getLogType())
                .timestamp(request.getTimestamp() != null ? request.getTimestamp() : LocalDateTime.now())
                .details(request.getDetails())
                .build();
        return proctoringLogRepository.save(log);
    }

    public List<ProctoringLog> getLogs(Integer examId, User user) {
        return proctoringLogRepository.findByUserAndExam(user, examRepository.getById(examId));
    }
}
