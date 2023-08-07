package com.ashe.popping.domain.termsagreement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashe.popping.domain.termsagreement.dto.TermsAgreementState;
import com.ashe.popping.domain.termsagreement.entity.TermsAgreement;

public interface TermsAgreementRepository extends JpaRepository<TermsAgreement, Long> {
	List<TermsAgreement> findByMemberIdAndStateIs(Long memberId, TermsAgreementState state);

	TermsAgreement findByTermsIdAndMemberId(Long termsId, Long memberId);
}
