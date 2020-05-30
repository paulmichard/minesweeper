package com.paulmichard.minesweeper.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;

import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class TimestampedEntity {
	@Column(name = "created_on", nullable = false)
	private Instant createdOn = Instant.now();
	@Column(name = "last_modified", nullable = false)
	private Instant lastModified = Instant.now();

	@PreUpdate
	public void updateLastModified() {
		lastModified = Instant.now();
	}
}
