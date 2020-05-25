package io.pdsi.virtualexam.web.controller;

import io.pdsi.virtualexam.core.jpa.entity.StudentEntry;
import io.pdsi.virtualexam.web.service.StudentEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentEntryController {
	@Autowired
	private StudentEntryService studentEntryService;

	@GetMapping("/studentEntries")
	@CrossOrigin
	public List<StudentEntry> getStudentEntries() {
		return studentEntryService.findAll();
	}
}
