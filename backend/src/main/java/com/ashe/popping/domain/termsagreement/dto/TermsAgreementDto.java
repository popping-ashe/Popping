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

	private String agreement;

	private LocalDateTime agreementDate;

	@Builder
	public TermsAgreementDto(Long termsAgreementId, Long memberId, Long termsId, String agreement,
		LocalDateTime agreementDate) {
		this.termsAgreementId = termsAgreementId;
		this.memberId = memberId;
		this.termsId = termsId;
		this.agreement = agreement;
		this.agreementDate = agreementDate;
	}

	public static TermsAgreementDto from(TermsAgreement termsAgreement) {
		return TermsAgreementDto.builder()
			.termsAgreementId(termsAgreement.getTermsAgreementId())
			.termsId(termsAgreement.getTermsId())
			.memberId(termsAgreement.getMemberId())
			.agreement(termsAgreement.getAgreement())
			.agreementDate(termsAgreement.getAgreementDate())
			.build();
	}

	public static TermsAgreementDto from(TermsAgreementApiDto.Request request) {
		return TermsAgreementDto.builder()
			.termsId(request.getTermsId())
			.memberId(request.getMemberId())
			.agreement(request.getAgreement())
			.build();
	}
}
