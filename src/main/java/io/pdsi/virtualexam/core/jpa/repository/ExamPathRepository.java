package io.pdsi.virtualexam.core.jpa.repository;

import io.pdsi.virtualexam.core.jpa.entity.Exam;
import io.pdsi.virtualexam.core.jpa.entity.ExamPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamPathRepository extends JpaRepository<ExamPath, Integer> {
	List<ExamPath> findAllByExamId(Integer examId);
}
