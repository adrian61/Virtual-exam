package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.core.jpa.entity.Examiner;

public interface ExaminerService {
	void save(Examiner examiner);

	Examiner findByLogin(String login);

	Examiner findByFirstNameAndLastName(String firstName, String lastName);
}
