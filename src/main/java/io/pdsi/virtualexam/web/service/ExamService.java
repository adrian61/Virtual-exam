package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.core.jpa.entity.Exam;

import java.util.List;

public interface ExamService {
	List<Exam> findAll();
	void saveExam(Exam exam);

	Exam getExam(Integer id);

	void deleteExam(Integer id);
}
