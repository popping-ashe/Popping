package com.ashe.popping.domain.termsagreement.dto;

import lombok.Getter;

@Getter
public enum TermsAgreementState {
	ACTIVE("동의"),
	PENDING("보류"),
	REJECTED("미동의");

	private final String termsAgreementState;

	TermsAgreementState(String termsAgreementState) {
		this.termsAgreementState = termsAgreementState;
	}
}
