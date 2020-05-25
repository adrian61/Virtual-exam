package io.pdsi.virtualexam.core.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	protected Integer id;
	@Column(updatable = false)
	private ZonedDateTime dateCreated;

	private ZonedDateTime dateLastUpdated;

	@PrePersist
	public void onCreate() {
		this.dateCreated = ZonedDateTime.now();
		this.dateLastUpdated = ZonedDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		this.dateLastUpdated = ZonedDateTime.now();
	}
}
