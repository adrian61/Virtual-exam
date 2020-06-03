package io.pdsi.virtualexam.api.dto;

import io.pdsi.virtualexam.core.jpa.entity.Exam;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
	private Integer id;
	@NotEmpty(message = "Title shouldn't be empty")
	private String title;
	@Size(min = 2, max = 64, message = "Password length should be between 2 and 64")
	private String password;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private MultipartFile file;


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
