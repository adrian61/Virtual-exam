package io.pdsi.virtualexam.web.service;

import java.util.List;

public interface ExamPathService {
	void saveGroups(Integer examId, List<String> path);
}
