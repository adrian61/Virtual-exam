package io.pdsi.virtualexam.core.jpa.repository;

import io.pdsi.virtualexam.core.jpa.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
}
