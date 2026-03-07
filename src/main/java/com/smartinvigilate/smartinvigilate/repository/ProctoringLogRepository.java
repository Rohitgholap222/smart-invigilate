package com.smartinvigilate.smartinvigilate.repository;

import com.smartinvigilate.smartinvigilate.model.ProctoringLog;
import com.smartinvigilate.smartinvigilate.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProctoringLogRepository extends JpaRepository<ProctoringLog, Integer> {
    List<ProctoringLog> findByExam(Exam exam);
}
