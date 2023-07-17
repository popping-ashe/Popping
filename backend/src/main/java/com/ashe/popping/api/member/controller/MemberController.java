package com.ashe.popping.api.member.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
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

		MemberDto.Response result = new ModelMapper().map(memberDto, MemberDto.Response.class);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PatchMapping("/{memberId}")
	public ResponseEntity<MemberDto.Response> modifyMember(@PathVariable("memberId") Long memberId, @RequestBody MemberDto.Request member) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		MemberDto memberDto = mapper.map(member, MemberDto.class);
		memberDto.setMemberId(memberId);

		memberService.updateMember(memberDto);

		MemberDto.Response result = new ModelMapper().map(memberDto, MemberDto.Response.class);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
