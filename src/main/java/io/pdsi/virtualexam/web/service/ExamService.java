package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.api.dto.ExamDto;
import io.pdsi.virtualexam.core.jpa.entity.Exam;
import io.pdsi.virtualexam.core.jpa.entity.Examiner;

import java.util.List;

public interface ExamService {
	List<Exam> findAll();

	void saveExam(Exam exam);

	Exam getExam(Integer id);

	List<ExamDto> getExamsByExaminer(Examiner examiner);

	void deleteExam(Integer id);
}
