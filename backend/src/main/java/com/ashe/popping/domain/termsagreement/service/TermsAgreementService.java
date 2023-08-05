package com.ashe.popping.domain.termsagreement.service;

import java.util.List;

import com.ashe.popping.domain.termsagreement.dto.TermsAgreementDto;

public interface TermsAgreementService {
	TermsAgreementDto createTermsAgreement(TermsAgreementDto termsAgreementDto);
	List<TermsAgreementDto> getTermsAgreementByMember(Long memberId);
}
