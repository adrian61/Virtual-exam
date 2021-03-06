package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.api.dto.ExamDto;
import io.pdsi.virtualexam.core.jpa.entity.Exam;
import io.pdsi.virtualexam.core.jpa.entity.Examiner;

import java.util.List;

public interface ExamService {
	List<Exam> findAll();

	void saveExam(ExamDto exam);

	void saveExamForExaminer(ExamDto exam, Examiner examiner);

	Exam getExam(Integer id);

	ExamDto getExamByTitle(String title);

	Exam getExamByExaminerAndTitle(Examiner examiner, String title);

	List<ExamDto> getExamsByExaminer(Examiner examiner);

	void deleteExam(Integer id);
}
