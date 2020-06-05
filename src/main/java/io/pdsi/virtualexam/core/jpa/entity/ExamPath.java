package io.pdsi.virtualexam.core.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Builder
@Table(name = "exampath", schema = "public")
public class ExamPath {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;
	@Column(name = "exam_id")
	Integer examId;
	@Column(name = "group_id")
	Integer groupId;
	@Column(name = "path")
	String path;

}
