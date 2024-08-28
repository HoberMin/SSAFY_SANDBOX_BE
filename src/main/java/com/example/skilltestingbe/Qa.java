package com.example.skilltestingbe;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Qa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "qa_id")
	private Long id;

	@Column(nullable = false)
	private String classNumber;

	@Column(nullable = false)
	private String region;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String content;

	@CreatedDate
	private LocalDateTime createdAt;
}
