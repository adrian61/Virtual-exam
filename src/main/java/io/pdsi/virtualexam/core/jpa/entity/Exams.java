package io.pdsi.virtualexam.core.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exams", schema = "public")
public class Exams extends BaseEntity {
	@Column(name = "title")
	private String title;

	@Column(name = "password")
	private String password;

	@Column(name = "start_date")
	private ZonedDateTime startDate;

	@Column(name = "end_date")
	private ZonedDateTime endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examiner_id")
	private Examiner examinerId;
}
