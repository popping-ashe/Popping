package com.ashe.popping.domain.termsagreement.entity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ashe.popping.domain.termsagreement.dto.TermsAgreementDto;
import com.ashe.popping.domain.termsagreement.constant.TermsAgreementState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class TermsAgreement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long termsAgreementId;

	@Column(nullable = false)
	private Long memberId;

	@Column(nullable = false)
	private Long termsId;

	@Enumerated(EnumType.STRING)
	private TermsAgreementState state;

	@Column(nullable = true)
	private LocalDateTime agreementDate;

	@Builder
	public TermsAgreement(Long termsAgreementId, Long memberId, Long termsId, TermsAgreementState state,
		LocalDateTime agreementDate) {
		this.termsAgreementId = termsAgreementId;
		this.memberId = memberId;
		this.termsId = termsId;
		this.state = state;
		this.agreementDate = agreementDate;
	}

	public static TermsAgreement from(TermsAgreementDto termsAgreementDto) {
		return TermsAgreement.builder()
			.termsAgreementId(termsAgreementDto.getTermsAgreementId())
			.termsId(termsAgreementDto.getTermsId())
			.memberId(termsAgreementDto.getMemberId())
			.state(termsAgreementDto.getState())
			.agreementDate(termsAgreementDto.getAgreementDate())
			.build();
	}

	public static TermsAgreement of(TermsAgreementDto termsAgreementDto, LocalDateTime agreementDate) {
		return TermsAgreement.builder()
			.termsAgreementId(termsAgreementDto.getTermsAgreementId())
			.termsId(termsAgreementDto.getTermsId())
			.memberId(termsAgreementDto.getMemberId())
			.state(termsAgreementDto.getState())
			.agreementDate(agreementDate)
			.build();
	}
	
	public static TermsAgreement of(TermsAgreementDto termsAgreementDto, TermsAgreementState state) {
		return TermsAgreement.builder()
			.termsAgreementId(termsAgreementDto.getTermsAgreementId())
			.termsId(termsAgreementDto.getTermsId())
			.memberId(termsAgreementDto.getMemberId())
			.state(state)
			.agreementDate(termsAgreementDto.getAgreementDate())
			.build();
	}

	public void updateAgreement(TermsAgreementState state) {
		this.state = state;
	}

	public void updateAgreementDate(LocalDateTime agreementDate) {
		this.agreementDate = agreementDate;
	}
}
