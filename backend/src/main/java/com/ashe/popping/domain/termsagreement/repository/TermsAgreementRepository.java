package com.ashe.popping.domain.termsagreement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashe.popping.domain.termsagreement.constant.TermsAgreementState;
import com.ashe.popping.domain.termsagreement.entity.TermsAgreement;

public interface TermsAgreementRepository extends JpaRepository<TermsAgreement, Long> {
	List<TermsAgreement> findByMemberIdAndStateIs(Long memberId, TermsAgreementState state);

	Optional<TermsAgreement> findByTermsIdAndMemberId(Long termsId, Long memberId);

	Long deleteByTermsId(Long termsId);
}
