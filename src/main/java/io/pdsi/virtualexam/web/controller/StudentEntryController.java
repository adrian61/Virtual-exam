package io.pdsi.virtualexam.web.controller;

import io.pdsi.virtualexam.core.jpa.entity.StudentEntry;
import io.pdsi.virtualexam.web.service.StudentEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudentEntryController {
	private final StudentEntryService studentEntryService;

	@GetMapping("/studentEntries")
	@CrossOrigin
	public List<StudentEntry> getStudentEntries() {
		return studentEntryService.findAll();
	}
}
