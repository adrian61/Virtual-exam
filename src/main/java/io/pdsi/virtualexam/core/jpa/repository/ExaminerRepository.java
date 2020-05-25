package io.pdsi.virtualexam.core.jpa.repository;

import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExaminerRepository extends JpaRepository<Examiner, Integer> {
	@Query("SELECT e FROM Examiner e " + "WHERE e.login = :login ")
	Examiner findByLogin(@Param("login") String login);

	Examiner findByFirstNameAndLastName(String firstName, String lastName);
}
