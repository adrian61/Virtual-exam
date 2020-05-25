package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.core.jpa.entity.StudentEntry;
import io.pdsi.virtualexam.core.jpa.repository.StudentEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentEntryServiceImpl implements StudentEntryService {
	@Autowired
	StudentEntryRepository studentEntryRepository;

	@Override
	public List<StudentEntry> findAll() {
		return studentEntryRepository.findAll();
	}
}
