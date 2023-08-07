package com.ashe.popping.domain.termsagreement.dto;

import java.time.LocalDateTime;

import com.ashe.popping.api.termsagreement.dto.TermsAgreementApiDto;
import com.ashe.popping.domain.termsagreement.entity.TermsAgreement;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TermsAgreementDto {
	private Long termsAgreementId;

	private Long memberId;

	private Long termsId;

	private TermsAgreementState state;

	private LocalDateTime agreementDate;

	@Builder
	public TermsAgreementDto(Long termsAgreementId, Long memberId, Long termsId, TermsAgreementState state,
		LocalDateTime agreementDate) {
		this.termsAgreementId = termsAgreementId;
		this.memberId = memberId;
		this.termsId = termsId;
		this.state = state;
		this.agreementDate = agreementDate;
	}

	public static TermsAgreementDto from(TermsAgreement termsAgreement) {
		return TermsAgreementDto.builder()
			.termsAgreementId(termsAgreement.getTermsAgreementId())
			.termsId(termsAgreement.getTermsId())
			.memberId(termsAgreement.getMemberId())
			.state(termsAgreement.getState())
			.agreementDate(termsAgreement.getAgreementDate())
			.build();
	}

	public static TermsAgreementDto from(TermsAgreementApiDto.Request request) {
		return TermsAgreementDto.builder()
			.termsId(request.getTermsId())
			.memberId(request.getMemberId())
			.state(request.getState())
			.build();
	}
}
