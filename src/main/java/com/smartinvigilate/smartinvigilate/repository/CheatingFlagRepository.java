package com.smartinvigilate.smartinvigilate.repository;

import com.smartinvigilate.smartinvigilate.model.CheatingFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CheatingFlagRepository extends JpaRepository<CheatingFlag, Integer> {
    List<CheatingFlag> findByUserIdAndExamId(Integer userId, Integer examId);
    List<CheatingFlag> findByExamId(Integer examId);
}
