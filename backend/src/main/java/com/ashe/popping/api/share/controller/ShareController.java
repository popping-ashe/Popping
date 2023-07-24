package com.ashe.popping.api.share.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.share.dto.ShareApiDto;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.entity.Member;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.service.MessageService;
import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.AuthenticationException;
import com.ashe.popping.global.resolver.memberinfo.MemberInfo;
import com.ashe.popping.global.resolver.memberinfo.MemberInfoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/share")
@RequiredArgsConstructor
public class ShareController {

	private final MemberService memberService;
	private final MessageService messageService;

	@GetMapping
	public ResponseEntity<Long> getShareId(@MemberInfo MemberInfoDto memberInfoDto) {
		Long memberId = memberInfoDto.getMemberId();
		MemberDto memberDto = memberService.getMemberByMemberId(memberId);
		return ResponseEntity.ok(memberDto.getShareId());
	}

	@GetMapping("/{shareId}")
	public ResponseEntity<List<ShareApiDto.Response>> getReceivedMessage(@PathVariable Long shareId) {
		Optional<Member> optionalMember = memberService.getMemberByShareId(shareId);
		if (optionalMember.isEmpty()) {
			throw new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER);
		} else {
			Member shareMember = optionalMember.get();
			List<MessageDto> messages = messageService.loadReceiveMessage(shareMember.getMemberId());
			return ResponseEntity.ok(toMessageResponse(messages));
		}
	}

	private static List<ShareApiDto.Response> toMessageResponse(List<MessageDto> messages) {
		return messages.stream()
			.map(ShareApiDto.Response::from)
			.toList();
	}
}
