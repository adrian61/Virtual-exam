package io.pdsi.virtualexam.api.dto;

import io.pdsi.virtualexam.core.jpa.entity.Exam;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class ExamDto {
	private Integer id;
	private String title;
	private ZonedDateTime startDate;

	public static ExamDto fromEntity(Exam exam) {
		return ExamDto.builder()
				.id(exam.getId())
				.title(exam.getTitle())
				.startDate(exam.getStartDate())
				.build();
	}

	public Exam toEntity() {
		Exam exam = Exam.builder()
				.title(this.getTitle())
				.startDate(this.getStartDate())
				.build();
		exam.setId(this.getId());
		return exam;
	}
}
