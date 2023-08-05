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
	public List<TermsDto> getAllTerms() {
		List<Terms> terms = termsRepository.findAll();
		return terms.stream().map(TermsDto::from).toList();
	}
}
