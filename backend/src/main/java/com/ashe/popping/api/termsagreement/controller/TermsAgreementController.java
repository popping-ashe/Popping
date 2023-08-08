package com.ashe.popping.api.termsagreement.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.termsagreement.dto.TermsAgreementApiDto;
import com.ashe.popping.domain.terms.service.TermsService;
import com.ashe.popping.domain.termsagreement.dto.TermsAgreementDto;
import com.ashe.popping.domain.termsagreement.dto.TermsAgreementState;
import com.ashe.popping.domain.termsagreement.service.TermsAgreementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/terms-agreement")
@RequiredArgsConstructor
public class TermsAgreementController {
	private final TermsAgreementService termsAgreementService;
	private final TermsService termsService;

	@PostMapping
	public ResponseEntity<TermsAgreementApiDto.Response> createTermsAgreement(
		@RequestBody TermsAgreementApiDto.Request request) {
		TermsAgreementDto termsAgreementDto = termsAgreementService.createTermsAgreement(
			TermsAgreementDto.from(request));
		return ResponseEntity.ok(
			TermsAgreementApiDto.Response.of(termsService.getTerms(request.getTermsId()), termsAgreementDto));
	}

	@PatchMapping
	public ResponseEntity<List<TermsAgreementApiDto.Response>> updateTermsAgreement(
		@RequestBody List<TermsAgreementApiDto.Request> request) {
		List<TermsAgreementApiDto.Response> responses = new LinkedList<>();
		request.forEach(r -> responses.add(TermsAgreementApiDto.Response.of(termsService.getTerms(r.getTermsId()),
			termsAgreementService.updateTermsAgreement(TermsAgreementDto.from(r)))));
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/pending/{id}")
	public ResponseEntity<List<TermsAgreementApiDto.Response>> getTermsAgreementIsPendingByMember(
		@PathVariable("id") Long memberId) {
		List<TermsAgreementDto> termsAgreement = termsAgreementService.getTermsAgreementByMember(memberId,
			TermsAgreementState.PENDING);
		List<TermsAgreementApiDto.Response> termsAgreementApiDto = termsAgreement.stream()
			.map(t -> TermsAgreementApiDto.Response.of(
				termsService.getTerms(t.getTermsId()), t))
			.toList();
		return ResponseEntity.ok(termsAgreementApiDto);
	}
}
