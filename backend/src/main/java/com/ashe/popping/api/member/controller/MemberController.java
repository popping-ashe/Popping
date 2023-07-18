package com.ashe.popping.api.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/{memberId}")
	public ResponseEntity<MemberDto.Response> getMember(@PathVariable("memberId") Long memberId) {
		MemberDto memberDto = memberService.getMemberByMemberId(memberId);

		MemberDto.Response response = MemberDto.Response.from(memberDto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PatchMapping("/{memberId}")
	public ResponseEntity<MemberDto.Response> modifyMember(@PathVariable("memberId") Long memberId,
		@RequestBody MemberDto.Request request) {
		MemberDto memberDto = MemberDto.of(request, memberId);

		MemberDto result = memberService.updateMember(memberDto);

		MemberDto.Response response = MemberDto.Response.from(result);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
