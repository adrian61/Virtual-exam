package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.api.dto.ExamDto;
import io.pdsi.virtualexam.core.jpa.entity.Exam;
import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import io.pdsi.virtualexam.core.jpa.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
	private final ExamRepository examRepository;

	@Override
	public List<Exam> findAll() {
		return examRepository.findAll();
	}

	@Override
	public void saveExam(Exam exam) {
		examRepository.save(exam);
	}

	@Override
	public Exam getExam(Integer id) {
		return examRepository.findById(id).orElse(null);
	}

	@Override
	public List<ExamDto> getExamsByExaminer(Examiner examiner) {
		System.out.println(examRepository.findAllByExaminerId(examiner));

		return examRepository.findAllByExaminerId(examiner)
				.stream()
				.map(ExamDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteExam(Integer id) {
		examRepository.deleteById(id);
	}
}
