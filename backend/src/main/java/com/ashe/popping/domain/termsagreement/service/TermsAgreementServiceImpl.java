package com.ashe.popping.domain.termsagreement.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashe.popping.domain.termsagreement.dto.TermsAgreementDto;
import com.ashe.popping.domain.termsagreement.entity.TermsAgreement;
import com.ashe.popping.domain.termsagreement.repository.TermsAgreementRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TermsAgreementServiceImpl implements TermsAgreementService{

	private final TermsAgreementRepository termsAgreementRepository;

	@Override
	public TermsAgreementDto createTermsAgreement(TermsAgreementDto termsAgreementDto) {
		TermsAgreement termsAgreement = null;
		if(termsAgreementDto.getAgreement().equals(("Y"))) {
			termsAgreement = TermsAgreement.of(termsAgreementDto, LocalDateTime.now());
		} else {
			termsAgreement = TermsAgreement.from(termsAgreementDto);
		}
		termsAgreementRepository.save(termsAgreement);
		return TermsAgreementDto.from(termsAgreement);
	}

	@Override
	public List<TermsAgreementDto> getTermsAgreementByMember(Long memberId) {
		List<TermsAgreement> termsAgreement = termsAgreementRepository.findByMemberId(memberId);
		return termsAgreement.stream().map(TermsAgreementDto::from).toList();
	}
}
