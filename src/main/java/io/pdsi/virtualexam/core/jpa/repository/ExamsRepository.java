package io.pdsi.virtualexam.core.jpa.repository;

import io.pdsi.virtualexam.core.jpa.entity.Exams;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamsRepository extends JpaRepository<Exams, Integer> {
}
