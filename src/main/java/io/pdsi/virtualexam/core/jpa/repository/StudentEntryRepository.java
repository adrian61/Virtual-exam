package io.pdsi.virtualexam.core.jpa.repository;

import io.pdsi.virtualexam.core.jpa.entity.StudentEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEntryRepository extends JpaRepository<StudentEntry, Integer> {
}
