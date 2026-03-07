package com.smartinvigilate.smartinvigilate.repository;

import com.smartinvigilate.smartinvigilate.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
