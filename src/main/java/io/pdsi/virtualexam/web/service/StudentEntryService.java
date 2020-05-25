package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.core.jpa.entity.StudentEntry;

import java.util.List;

public interface StudentEntryService {
	List<StudentEntry> findAll();
}
