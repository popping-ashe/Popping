package com.ashe.popping.api.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.member.dto.MemberApiDto;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/{memberId}")
	public ResponseEntity<MemberApiDto.Response> getMember(@PathVariable("memberId") Long memberId) {
		MemberDto memberDto = memberService.getMemberByMemberId(memberId);

		MemberApiDto.Response response = MemberApiDto.Response.from(memberDto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PatchMapping("/{memberId}")
	public ResponseEntity<MemberApiDto.Response> modifyMember(@PathVariable("memberId") Long memberId,
		@RequestBody MemberApiDto.Request request) {
		MemberApiDto memberApiDto = MemberApiDto.of(request, memberId);

		MemberDto result = memberService.updateMember(MemberDto.from(memberApiDto));

		MemberApiDto.Response response = MemberApiDto.Response.from(result);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
