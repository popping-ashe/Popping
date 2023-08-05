package com.ashe.popping.domain.termsagreement.dto;

import java.time.LocalDateTime;

import com.ashe.popping.domain.termsagreement.entity.TermsAgreement;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
