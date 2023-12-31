package com.ashe.popping.domain.terms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashe.popping.domain.terms.dto.TermsDto;
import com.ashe.popping.domain.terms.entity.Terms;
import com.ashe.popping.domain.terms.repository.TermsRepository;
import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.BusinessException;

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
		Optional<Terms> optionalTerms = termsRepository.findByTermsId(termsId);
		if (optionalTerms.isEmpty())
			throw new BusinessException(ErrorCode.NOT_EXIST_TERMS);
		return TermsDto.from(optionalTerms.get());
	}

	@Override
	public TermsDto updateTerms(TermsDto termsDto) {
		Optional<Terms> optionalTerms = termsRepository.findByTermsId(termsDto.getTermsId());

		if (optionalTerms.isEmpty())
			throw new BusinessException(ErrorCode.NOT_EXIST_TERMS);

		Terms terms = optionalTerms.get();
		if (termsDto.getTitle() != null)
			terms.updateTitle(termsDto.getTitle());
		if (termsDto.getContent() != null)
			terms.updateContent(termsDto.getContent());
		if (termsDto.getMandatory() != null)
			terms.updateMandatory(termsDto.getMandatory());

		terms.updateUpdatedTime(LocalDateTime.now());

		return TermsDto.from(terms);
	}

	@Override
	public Long deleteTerms(Long termsId) {
		return termsRepository.deleteByTermsId(termsId);
	}
}
