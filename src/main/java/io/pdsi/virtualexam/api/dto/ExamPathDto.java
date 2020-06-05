package io.pdsi.virtualexam.api.dto;

import io.pdsi.virtualexam.core.jpa.entity.ExamPath;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExamPathDto {
	private Integer id;
	private Integer examId;
	private Integer groupId;
	private String path;

	public static ExamPathDto fromEntity(ExamPath examPath) {
		return ExamPathDto.builder()
				.id(examPath.getId())
				.examId(examPath.getExamId())
				.groupId(examPath.getGroupId())
				.path(examPath.getPath())
				.build();
	}

	public ExamPath toEntity() {
		ExamPath examPath = ExamPath.builder()
				.examId(this.getExamId())
				.groupId(this.getGroupId())
				.path(this.getPath())
				.build();
		examPath.setId(this.getId());
		return examPath;
	}

}
