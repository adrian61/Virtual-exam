package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.core.jpa.entity.StudentEntry;
import io.pdsi.virtualexam.core.jpa.repository.StudentEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentEntryServiceImpl implements StudentEntryService {
	private final StudentEntryRepository studentEntryRepository;

	@Override
	public List<StudentEntry> findAll() {
		return studentEntryRepository.findAll();
	}
}
