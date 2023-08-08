package com.ashe.popping.domain.terms.service;

import java.util.List;

import com.ashe.popping.domain.terms.dto.TermsDto;

public interface TermsService {
	TermsDto createTerms(TermsDto termsDto);

	List<TermsDto> getAllTerms();

	TermsDto getTerms(Long termsId);

	TermsDto modifyTerms(TermsDto termsDto);
}
