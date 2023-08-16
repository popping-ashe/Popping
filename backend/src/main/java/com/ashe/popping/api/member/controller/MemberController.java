package com.ashe.popping.api.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.member.dto.MemberApiDto;
import com.ashe.popping.api.termsagreement.dto.TermsAgreementApiDto;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.domain.message.service.MessageService;
import com.ashe.popping.domain.terms.service.TermsService;
import com.ashe.popping.domain.termsagreement.dto.TermsAgreementDto;
import com.ashe.popping.domain.termsagreement.constant.TermsAgreementState;
import com.ashe.popping.domain.termsagreement.service.TermsAgreementService;
import com.ashe.popping.global.resolver.memberinfo.MemberInfo;
import com.ashe.popping.global.resolver.memberinfo.MemberInfoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final MessageService messageService;
	private final TermsService termsService;
	private final TermsAgreementService termsAgreementService;

	@GetMapping("/me")
	public ResponseEntity<MemberApiDto.Response> getMember(@MemberInfo MemberInfoDto
		memberInfoDto) {
		Long memberId = memberInfoDto.getMemberId();
		MemberDto memberDto = memberService.getMemberByMemberId(memberId);
		Long expireMessageCount = messageService.countExpireMessage(memberDto.getMemberId(),
			memberDto.getLastVisitedTime());

		List<TermsAgreementDto> termsAgreement = termsAgreementService.getTermsAgreementByMember(memberId,
			TermsAgreementState.PENDING);
		List<TermsAgreementApiDto.Response> termsAgreementApiDto = termsAgreement.stream()
			.map(t -> TermsAgreementApiDto.Response.of(
				termsService.getTerms(t.getTermsId()), t))
			.toList();
		MemberApiDto.Response response = MemberApiDto.Response.of(memberDto, expireMessageCount, termsAgreementApiDto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PatchMapping("/me")
	public ResponseEntity<MemberApiDto.Response> modifyNickname(@MemberInfo MemberInfoDto
		memberInfoDto,
		@RequestBody MemberApiDto.Request request) {
		Long memberId = memberInfoDto.getMemberId();
		MemberApiDto memberApiDto = MemberApiDto.of(request, memberId);

		MemberDto memberDto = memberService.updateNickname(MemberDto.from(memberApiDto));
		Long expireMessageCount = messageService.countExpireMessage(memberDto.getMemberId(),
			memberDto.getLastVisitedTime());

		MemberApiDto.Response response = MemberApiDto.Response.of(memberDto, expireMessageCount);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PatchMapping("/me/bio")
	public ResponseEntity<?> updateMemberBio(@MemberInfo MemberInfoDto memberInfoDto,
		@RequestBody MemberApiDto.UpdateBioRequest request) {
		Long memberId = memberInfoDto.getMemberId();
		MemberDto memberDto = memberService.updateBio(MemberDto.of(request, memberId));
		return ResponseEntity.ok(MemberApiDto.Response.from(memberDto));
	}

}
