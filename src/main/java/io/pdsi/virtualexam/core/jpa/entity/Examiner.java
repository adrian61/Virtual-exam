package io.pdsi.virtualexam.core.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "examiner", schema = "public")
public class Examiner extends BaseEntity{
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "login")
	private String login;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;

	@Transient
	private String passwordConfirm;
}
