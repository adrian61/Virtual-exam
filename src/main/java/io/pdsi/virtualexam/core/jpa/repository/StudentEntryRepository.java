package io.pdsi.virtualexam.core.jpa.repository;

import io.pdsi.virtualexam.core.jpa.entity.Exam;
import io.pdsi.virtualexam.core.jpa.entity.StudentEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentEntryRepository extends JpaRepository<StudentEntry, Integer> {
	List<StudentEntry> findAllByExam(Exam exam);
}
