package com.example.skilltestingbe;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
