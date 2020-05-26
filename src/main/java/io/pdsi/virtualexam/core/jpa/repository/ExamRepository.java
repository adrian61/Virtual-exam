package io.pdsi.virtualexam.core.jpa.repository;

import io.pdsi.virtualexam.core.jpa.entity.Exam;
import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
	List<Exam> findAllByExaminerId(Examiner examiner);
}
