package io.pdsi.virtualexam.api.dto;

import io.pdsi.virtualexam.core.jpa.entity.Exam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ExamDto {
	private Integer id;
	private String title;
	private String password;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;


	public static ExamDto fromEntity(Exam exam) {
		return ExamDto.builder()
				.id(exam.getId())
				.title(exam.getTitle())
				.password(exam.getPassword())
				.startDate(exam.getStartDate())
				.endDate(exam.getEndDate())
				.build();
	}


	public Exam toEntity() {
		Exam exam = Exam.builder()
				.title(this.getTitle())
				.password(this.getPassword())
				.startDate(this.getStartDate())
				.endDate(this.getEndDate())
				.build();
		exam.setId(this.getId());
		return exam;
	}
}
