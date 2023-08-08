package com.ashe.popping.domain.terms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashe.popping.domain.terms.dto.TermsDto;
import com.ashe.popping.domain.terms.entity.Terms;
import com.ashe.popping.domain.terms.repository.TermsRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TermsServiceImpl implements TermsService {

	private final TermsRepository termsRepository;

	@Override
	public TermsDto createTerms(TermsDto termsDto) {
		Terms terms = Terms.from(termsDto);
		termsRepository.save(terms);
		return TermsDto.from(terms);
	}

	@Override
	public List<TermsDto> getAllTerms() {
		List<Terms> terms = termsRepository.findAll();
		return terms.stream().map(TermsDto::from).toList();
	}

	@Override
	public TermsDto getTerms(Long termsId) {
		return TermsDto.from(termsRepository.findByTermsId(termsId));
	}

	@Override
	public TermsDto modifyTerms(TermsDto termsDto) {
		Terms terms = termsRepository.findByTermsId(termsDto.getTermsId());
		if (termsDto.getTitle() != null)
			terms.updateTitle(termsDto.getTitle());
		if (termsDto.getContent() != null)
			terms.updateContent(termsDto.getContent());
		if (termsDto.getMandatory() != null)
			terms.updateMandatory(termsDto.getMandatory());

		return TermsDto.from(terms);
	}

	@Override
	public Long deleteTerms(Long termsId) {
		return termsRepository.deleteByTermsId(termsId);
	}
}
