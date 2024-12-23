package com.example.skilltestingbe.api.qa.repository.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "qa")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Qa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "qa_id")
	private Long id;

	@Column(nullable = false, name = "class_number")
	private String classNumber;

	@Column(nullable = false)
	private String region;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String content;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;
}
