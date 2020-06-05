package io.pdsi.virtualexam.web.service;

import java.util.ArrayList;

public interface ExamPathService {
	void saveGroups(Integer examId, ArrayList<String> path);
}
