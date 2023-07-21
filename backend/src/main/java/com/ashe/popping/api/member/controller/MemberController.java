package com.ashe.popping.api.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.member.dto.MemberApiDto;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.domain.message.service.MessageService;
import com.ashe.popping.global.resolver.memberinfo.MemberInfo;
import com.ashe.popping.global.resolver.memberinfo.MemberInfoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final MessageService messageService;

	@GetMapping("/me")
	public ResponseEntity<MemberApiDto.Response> getMember(@MemberInfo MemberInfoDto
		memberInfoDto) {
		Long memberId = memberInfoDto.getMemberId();
		MemberDto memberDto = memberService.getMemberByMemberId(memberId);
		Long expireMessageCount = messageService.countExpireMessage(memberDto.getMemberId(),
			memberDto.getLastVisitedTime());

		MemberApiDto.Response response = MemberApiDto.Response.of(memberDto, expireMessageCount);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PatchMapping("/me")
	public ResponseEntity<MemberApiDto.Response> modifyMember(@MemberInfo MemberInfoDto
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

}
