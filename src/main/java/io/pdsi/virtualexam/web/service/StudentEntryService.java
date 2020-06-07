package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.core.jpa.entity.Exam;
import io.pdsi.virtualexam.core.jpa.entity.StudentEntry;

import java.util.List;

public interface StudentEntryService {
	List<StudentEntry> findAll();

	List<StudentEntry> findByExamId(Exam exam);

	void addNewStudent(StudentEntry studentEntry);
}
