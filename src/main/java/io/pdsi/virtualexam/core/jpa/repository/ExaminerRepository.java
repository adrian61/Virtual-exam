package io.pdsi.virtualexam.core.jpa.repository;

import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminerRepository extends JpaRepository<Examiner, Integer> {
	Examiner findByLogin(String login);

	Examiner findByFirstNameAndLastName(String firstName, String lastName);
}
