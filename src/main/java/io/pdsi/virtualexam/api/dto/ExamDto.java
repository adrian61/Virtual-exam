package io.pdsi.virtualexam.api.dto;

import io.pdsi.virtualexam.core.jpa.entity.Exam;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExamDto {
	private Integer id;
	private String title;

	public static ExamDto fromEntity(Exam exam) {
		return ExamDto.builder()
				.id(exam.getId())
				.title(exam.getTitle())
				.build();
	}

	public Exam toEntity() {
		Exam exam = Exam.builder()
				.title(this.getTitle())
				.build();
		exam.setId(this.getId());
		return exam;
	}
}
