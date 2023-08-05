package com.ashe.popping.api.termsagreement.controller;

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
import com.ashe.popping.domain.termsagreement.service.TermsAgreementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/terms-agreement")
@RequiredArgsConstructor
public class TermsAgreementController {
	private final TermsAgreementService termsAgreementService;
	private final TermsService termsService;

	@PostMapping
	public ResponseEntity<TermsAgreementApiDto.Response> createTermsAgreement(@RequestBody TermsAgreementApiDto.Request request) {
		TermsAgreementDto termsAgreementDto = termsAgreementService.createTermsAgreement(TermsAgreementDto.from(request));
		return ResponseEntity.ok(TermsAgreementApiDto.Response.of(termsService.getTerms(request.getTermsId()), termsAgreementDto));
	}

	@PatchMapping
	public ResponseEntity<TermsAgreementApiDto.Response> updateTermsAgreement(@RequestBody TermsAgreementApiDto.Request request) {
		TermsAgreementDto termsAgreementDto = termsAgreementService.updateTermsAgreement(TermsAgreementDto.from(request));
		return ResponseEntity.ok(TermsAgreementApiDto.Response.of(termsService.getTerms(request.getTermsId()), termsAgreementDto));
	}
}
