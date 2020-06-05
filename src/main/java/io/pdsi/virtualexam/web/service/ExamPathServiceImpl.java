package io.pdsi.virtualexam.web.service;

import io.pdsi.virtualexam.api.dto.ExamPathDto;
import io.pdsi.virtualexam.core.jpa.repository.ExamPathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamPathServiceImpl implements ExamPathService {
	private final ExamPathRepository examPathRepository;

	@Override
	public void saveGroups(Integer examId, List<String> pathList) {
		for (int i = 0; i < pathList.size(); i++) {
			ExamPathDto examPath = ExamPathDto.builder()
					.examId(examId)
					.groupId(i + 1)
					.path(pathList.get(i))
					.build();
			examPathRepository.save(examPath.toEntity());
		}
	}

	@Override
	public List<ExamPathDto> getGroupsForExam(Integer examId) {
		return examPathRepository.findAllByExamId(examId)
				.stream()
				.map(ExamPathDto::fromEntity)
				.collect(Collectors.toList());
	}
}
