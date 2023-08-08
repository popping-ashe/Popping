package com.ashe.popping.domain.terms.dto;

import java.time.LocalDateTime;

import com.ashe.popping.api.terms.dto.TermsApiDto;
import com.ashe.popping.domain.terms.entity.Terms;

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

	public static TermsDto of(TermsApiDto.Request termsApiDto, Long termsId) {
		return TermsDto.builder()
			.termsId(termsId)
			.title(termsApiDto.getTitle())
			.content(termsApiDto.getContent())
			.mandatory(termsApiDto.getMandatory())
			.build();
	}

	public static TermsDto from(Terms terms) {
		return TermsDto.builder()
			.termsId(terms.getTermsId())
			.title(terms.getTitle())
			.content(terms.getContent())
			.mandatory(terms.getMandatory())
			.createdTime(terms.getCreatedTime())
			.updatedTime(terms.getUpdatedTime())
			.build();
	}

	public static TermsDto from(TermsApiDto.Request request) {
		return TermsDto.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.mandatory(request.getMandatory())
			.build();
	}
}
