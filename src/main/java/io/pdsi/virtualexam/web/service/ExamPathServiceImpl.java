package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.core.jpa.repository.ExamPathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ExamPathServiceImpl {
	private final ExamPathRepository examPathRepository;

	void saveGroups(Integer examId, ArrayList<String> path){

	}
}
