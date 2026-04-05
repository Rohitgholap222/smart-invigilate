package com.smartinvigilate.smartinvigilate.service;

import com.smartinvigilate.smartinvigilate.dto.ExamRequest;
import com.smartinvigilate.smartinvigilate.dto.QuestionRequest;
import com.smartinvigilate.smartinvigilate.dto.RegisterRequest;
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
    private final UserRepository userRepository;
    private final CheatingFlagRepository cheatingFlagRepository;
    private final RoleRepository roleRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public List<User> getAllStudents() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole().getName().equalsIgnoreCase("STUDENT"))
                .collect(Collectors.toList());
    }

    public User updateStudent(Integer id, RegisterRequest request) {
        User student;
        if (id == null) {
            student = new User();
            student.setRole(roleRepository.findByName("STUDENT").orElseThrow());
        } else {
            student = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            if (!student.getRole().getName().equalsIgnoreCase("STUDENT")) {
                throw new RuntimeException("User is not a student");
            }
        }

        if (request.getStudentId() != null) student.setStudentId(request.getStudentId());
        if (request.getFirstName() != null) student.setFirstName(request.getFirstName());
        if (request.getLastName() != null) student.setLastName(request.getLastName());
        if (request.getRollNumber() != null) student.setRollNumber(request.getRollNumber());
        if (request.getRegistrationNumber() != null) student.setRegistrationNumber(request.getRegistrationNumber());
        if (request.getEmail() != null) student.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) student.setPhoneNumber(request.getPhoneNumber());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getDepartment() != null) student.setDepartment(request.getDepartment());
        if (request.getCourse() != null) student.setCourse(request.getCourse());
        if (request.getSemester() != null) student.setSemester(request.getSemester());
        if (request.getYear() != null) student.setYear(request.getYear());
        if (request.getCollegeName() != null) student.setCollegeName(request.getCollegeName());
        if (request.getIsActive() != null) student.setActive(request.getIsActive());

        return userRepository.save(student);
    }

    public void deleteStudent(Integer id) {
        User student = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (!student.getRole().getName().equalsIgnoreCase("STUDENT")) {
            throw new RuntimeException("User is not a student");
        }
        
        userRepository.delete(student);
    }

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

    public List<User> addStudentsBulk(List<RegisterRequest> requests) {
        Role studentRole = roleRepository.findByName("STUDENT").orElseThrow();
        return requests.stream()
                .map(req -> {
                    var user = User.builder()
                            .studentId(req.getStudentId())
                            .firstName(req.getFirstName())
                            .lastName(req.getLastName())
                            .email(req.getEmail())
                            .password(passwordEncoder.encode(req.getPassword()))
                            .role(studentRole)
                            .isActive(true)
                            .build();
                    return userRepository.save(user);
                }).collect(Collectors.toList());
    }

    public User getStudentById(Integer id) {
        return userRepository.findById(id)
                .filter(u -> u.getRole().getName().equalsIgnoreCase("STUDENT"))
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<User> searchStudents(String query) {
        // Simple search by email or name
        return userRepository.findAll().stream()
                .filter(u -> u.getRole().getName().equalsIgnoreCase("STUDENT"))
                .filter(u -> u.getEmail().contains(query) || u.getFirstName().contains(query) || u.getLastName().contains(query))
                .collect(Collectors.toList());
    }

    public User patchStudentStatus(Integer id, boolean isActive) {
        User student = getStudentById(id);
        student.setActive(isActive);
        return userRepository.save(student);
    }

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(Integer id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
    }

    public Exam updateExam(Integer id, ExamRequest request) {
        Exam exam = getExamById(id);
        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setStartTime(request.getStartTime());
        exam.setEndTime(request.getEndTime());
        return examRepository.save(exam);
    }

    public void deleteExam(Integer id) {
        examRepository.deleteById(id);
    }

    public Exam deactivateExam(Integer id) {
        Exam exam = getExamById(id);
        exam.setActive(false);
        return examRepository.save(exam);
    }

    public List<Question> getQuestionsByExamId(Integer examId) {
        return questionRepository.findByExamId(examId);
    }

    public Question updateQuestion(Integer id, QuestionRequest request) {
        Question q = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        q.setContent(request.getContent());
        q.setOptionA(request.getOptionA());
        q.setOptionB(request.getOptionB());
        q.setOptionC(request.getOptionC());
        q.setOptionD(request.getOptionD());
        q.setCorrectOption(request.getCorrectOption());
        return questionRepository.save(q);
    }

    public void deleteQuestion(Integer id) {
        questionRepository.deleteById(id);
    }

    public List<CheatingFlag> getCheatingFlags(Integer examId) {
        return cheatingFlagRepository.findByExamId(examId);
    }

    public void flagSubmission(Integer submissionId, String reason) {
        Submission s = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        CheatingFlag flag = CheatingFlag.builder()
                .submission(s)
                .user(s.getUser())
                .exam(s.getExam())
                .reason(reason)
                .severity("HIGH")
                .build();
        cheatingFlagRepository.save(flag);
        s.setCheatingFlag(true);
        submissionRepository.save(s);
    }
}
