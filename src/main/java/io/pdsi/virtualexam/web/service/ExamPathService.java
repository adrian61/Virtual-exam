package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.api.dto.ExamPathDto;
import io.pdsi.virtualexam.core.jpa.entity.ExamPath;

import java.util.List;

public interface ExamPathService {
	void saveGroups(Integer examId, List<String> path);
	List<ExamPathDto> getGroupsForExam(Integer examId);
}
