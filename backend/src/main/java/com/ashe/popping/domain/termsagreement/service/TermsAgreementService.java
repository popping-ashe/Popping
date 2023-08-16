package com.ashe.popping.domain.termsagreement.service;

import java.util.List;

import com.ashe.popping.domain.termsagreement.dto.TermsAgreementDto;
import com.ashe.popping.domain.termsagreement.constant.TermsAgreementState;

public interface TermsAgreementService {
	TermsAgreementDto createTermsAgreement(TermsAgreementDto termsAgreementDto);

	List<TermsAgreementDto> getTermsAgreementByMember(Long memberId, TermsAgreementState termsAgreementState);

	TermsAgreementDto updateTermsAgreement(TermsAgreementDto termsAgreementDto);

	Long deleteTermsAgreementByTermsId(Long termsId);

	Long deleteTermsAgreementByMemberId(Long memberId);
}
