package com.smartinvigilate.smartinvigilate.repository;

import com.smartinvigilate.smartinvigilate.model.Submission;
import com.smartinvigilate.smartinvigilate.model.User;
import com.smartinvigilate.smartinvigilate.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    Optional<Submission> findByUserAndExam(User user, Exam exam);
    java.util.List<Submission> findByUser(User user);
}
