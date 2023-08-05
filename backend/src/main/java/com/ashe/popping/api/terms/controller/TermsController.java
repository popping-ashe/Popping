package com.ashe.popping.api.terms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.terms.dto.TermsApiDto;
import com.ashe.popping.domain.terms.dto.TermsDto;
import com.ashe.popping.domain.terms.service.TermsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/terms")
@RequiredArgsConstructor
public class TermsController {
	private final TermsService termsService;

	@PostMapping
	public ResponseEntity<TermsApiDto.Response> createTerms(@RequestBody TermsApiDto.Request request) {
		TermsDto terms = termsService.createTerms(TermsDto.from(request));
		return ResponseEntity.ok(TermsApiDto.Response.from(terms));
	}

	@GetMapping
	public ResponseEntity<List<TermsApiDto.Response>> getAllTerms() {
		List<TermsDto> terms = termsService.getAllTerms();
		return ResponseEntity.ok(terms.stream().map(TermsApiDto.Response::from).toList());
	}
}
