package com.ashe.popping.api.terms.dto;

import java.time.LocalDateTime;

import com.ashe.popping.domain.terms.dto.TermsDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class TermsApiDto {
	@Builder
	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class Request {
		private String title;
		private String content;
		private String mandatory;
	}
	@Builder
	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class Response {
		private Long termsId;
		private String title;
		private String content;
		private String mandatory;
		private LocalDateTime createdTime;
		private LocalDateTime updatedTime;

		public static TermsApiDto.Response from(TermsDto termsDto) {
			return Response.builder()
				.termsId(termsDto.getTermsId())
				.title(termsDto.getTitle())
				.content(termsDto.getContent())
				.mandatory(termsDto.getMandatory())
				.createdTime(termsDto.getCreatedTime())
				.updatedTime(termsDto.getUpdatedTime())
				.build();
		}
	}
}
