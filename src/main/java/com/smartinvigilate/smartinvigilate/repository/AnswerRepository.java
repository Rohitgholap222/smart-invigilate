package com.smartinvigilate.smartinvigilate.repository;

import com.smartinvigilate.smartinvigilate.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByUserIdAndExamId(Integer userId, Integer examId);
    Optional<Answer> findByUserIdAndExamIdAndQuestionId(Integer userId, Integer examId, Integer questionId);
}
