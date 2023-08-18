package com.ashe.popping.api.share.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.share.dto.ShareApiDto;
import com.ashe.popping.api.share.dto.ShareApiResultDto;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.service.MessageService;
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
	public ResponseEntity<ShareApiDto.ShareResponse> getShareId(@MemberInfo MemberInfoDto memberInfoDto) {
		Long memberId = memberInfoDto.getMemberId();
		MemberDto memberDto = memberService.getMemberByMemberId(memberId);
		return ResponseEntity.ok(new ShareApiDto.ShareResponse(memberDto.getShareId()));
	}

	@GetMapping("/{shareId}")
	public ResponseEntity<ShareApiResultDto<List<ShareApiDto.MessageResponse>>> getReceivedMessage(
		@PathVariable Long shareId, Pageable pageable) {
		MemberDto member = memberService.getMemberByShareId(shareId);
		List<MessageDto> messages = messageService.loadReceiveMessage(member.getMemberId(), pageable);
		List<ShareApiDto.MessageResponse> messageResponse = toMessageResponse(messages);
		return ResponseEntity.ok(ShareApiResultDto.of(member.getNickname(), member.getBio(),messageResponse));
	}

	private static List<ShareApiDto.MessageResponse> toMessageResponse(List<MessageDto> messages) {
		return messages.stream()
			.map(ShareApiDto.MessageResponse::from)
			.toList();
	}
}
