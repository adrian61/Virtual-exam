package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.core.jpa.entity.Exams;

import java.util.List;

public interface ExamsService {
	List<Exams> findAll();
}
