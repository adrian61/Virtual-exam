package io.pdsi.virtualexam.api.dto;

import io.pdsi.virtualexam.core.jpa.entity.Examiner;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExaminerDto {
	private Integer id;
	private String firstName;
	private String lastName;

	public static ExaminerDto fromEntity(Examiner examiner) {
		return ExaminerDto.builder()
				.id(examiner.getId())
				.firstName(examiner.getFirstName())
				.lastName(examiner.getLastName())
				.build();
	}

	public Examiner toEntity() {
		Examiner examiner = Examiner.builder()
				.firstName(this.getFirstName())
				.lastName(this.getLastName())
				.build();
		examiner.setId(this.getId());
		return examiner;
	}

}
