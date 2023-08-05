package com.ashe.popping.domain.terms.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ashe.popping.domain.terms.dto.TermsDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Terms {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long termsId;

	@Column(nullable = false, length = 30)
	private String title;

	@Column(nullable = false, length = 1000)
	private String content;

	@Column(nullable = false, length = 1)
	private String mandatory;

	@CreatedDate
	private LocalDateTime createdTime;

	@Column(nullable = true)
	private LocalDateTime updatedTime;

	@Builder
	public Terms(Long termsId, String title, String content, String mandatory, LocalDateTime createdTime,
		LocalDateTime updatedTime) {
		this.termsId = termsId;
		this.title = title;
		this.content = content;
		this.mandatory = mandatory;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}
}

