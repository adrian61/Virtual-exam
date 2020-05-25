package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.core.jpa.entity.Exams;
import io.pdsi.virtualexam.core.jpa.repository.ExamsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExamsServiceImpl implements ExamsService {
	@Autowired
	private ExamsRepository examsRepository;

	@Override
	public List<Exams> findAll() {
		return examsRepository.findAll();
	}
}
