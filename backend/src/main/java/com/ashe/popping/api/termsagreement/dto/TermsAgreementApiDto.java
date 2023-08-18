package com.ashe.popping.api.termsagreement.dto;

import java.time.LocalDateTime;

import com.ashe.popping.domain.terms.dto.TermsDto;
import com.ashe.popping.domain.termsagreement.dto.TermsAgreementDto;
import com.ashe.popping.domain.termsagreement.constant.TermsAgreementState;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TermsAgreementApiDto {

	@Builder
	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class Request {
		private Long termsId;
		private Long memberId;
		private TermsAgreementState state;
	}

	@Builder
	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class Response {
		private Long termsAgreementId;
		private Long termsId;
		private String title;
		private String content;
		private String mandatory;
		private TermsAgreementState state;
		private LocalDateTime agreementDate;

		public static TermsAgreementApiDto.Response of(TermsDto termsDto, TermsAgreementDto termsAgreementDto) {
			return Response.builder()
				.termsAgreementId(termsAgreementDto.getTermsAgreementId())
				.termsId(termsAgreementDto.getTermsId())
				.state(termsAgreementDto.getState())
				.agreementDate(termsAgreementDto.getAgreementDate())
				.title(termsDto.getTitle())
				.content(termsDto.getContent())
				.mandatory(termsDto.getMandatory())
				.build();
		}
	}
}
