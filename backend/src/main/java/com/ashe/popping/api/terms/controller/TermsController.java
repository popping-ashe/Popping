package com.ashe.popping.api.terms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.terms.dto.TermsApiDto;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.domain.terms.dto.TermsDto;
import com.ashe.popping.domain.terms.service.TermsService;
import com.ashe.popping.domain.termsagreement.dto.TermsAgreementDto;
import com.ashe.popping.domain.termsagreement.dto.TermsAgreementState;
import com.ashe.popping.domain.termsagreement.service.TermsAgreementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/terms")
@RequiredArgsConstructor
public class TermsController {
	private final TermsService termsService;
	private final TermsAgreementService termsAgreementService;
	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<TermsApiDto.Response> createTerms(@RequestBody TermsApiDto.Request request) {
		TermsDto terms = termsService.createTerms(TermsDto.from(request));
		memberService.getAllMember()
			.forEach(m -> termsAgreementService.createTermsAgreement(
				TermsAgreementDto.of(m.getMemberId(), terms.getTermsId(), TermsAgreementState.PENDING)));
		return ResponseEntity.ok(TermsApiDto.Response.from(terms));
	}

	@GetMapping
	public ResponseEntity<List<TermsApiDto.Response>> getAllTerms() {
		List<TermsDto> terms = termsService.getAllTerms();
		return ResponseEntity.ok(terms.stream().map(TermsApiDto.Response::from).toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TermsApiDto.Response> getTerms(@PathVariable("id") Long termsId) {
		TermsDto terms = termsService.getTerms(termsId);

		return ResponseEntity.ok(TermsApiDto.Response.from(terms));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<TermsApiDto.Response> modifyTerms(@PathVariable("id") Long termsId,
		@RequestBody TermsApiDto.Request request) {
		TermsDto terms = termsService.modifyTerms(TermsDto.of(request, termsId));

		return ResponseEntity.ok(TermsApiDto.Response.from(terms));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> deleteTerms(@PathVariable("id") Long termsId) {
		return ResponseEntity.ok(termsService.deleteTerms(termsId));
	}
}
