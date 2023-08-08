package com.ashe.popping.api.signout.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.signout.service.SignoutService;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.global.resolver.memberinfo.MemberInfo;
import com.ashe.popping.global.resolver.memberinfo.MemberInfoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SignoutController {

	private final SignoutService signoutService;
	private final MemberService memberService;

	@DeleteMapping("/signout")
	public ResponseEntity<String> deleteMember(@MemberInfo MemberInfoDto memberInfoDto){
		MemberDto memberDto = memberService.getMemberByMemberId(memberInfoDto.getMemberId());
		signoutService.disconnectKakao(memberDto.getSocialLoginId());
		memberService.deleteMember(memberInfoDto.getMemberId());

		return ResponseEntity.ok(null);
	}
}
