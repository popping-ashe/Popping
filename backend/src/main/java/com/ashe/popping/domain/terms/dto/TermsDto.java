package com.ashe.popping.domain.terms.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TermsDto {
	private Long termsId;

	private String title;

	private String content;

	private String mandatory;

	private LocalDateTime createdTime;

	private LocalDateTime updatedTime;

	@Builder
	public TermsDto(Long termsId, String title, String content, String mandatory, LocalDateTime createdTime,
		LocalDateTime updatedTime) {
		this.termsId = termsId;
		this.title = title;
		this.content = content;
		this.mandatory = mandatory;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}
}
