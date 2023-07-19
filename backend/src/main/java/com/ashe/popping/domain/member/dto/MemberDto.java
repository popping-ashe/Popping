package com.ashe.popping.domain.member.dto;

import java.time.LocalDateTime;

import com.ashe.popping.api.member.dto.MemberApiDto;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {
	private Long memberId;
	private String nickname;
	private LocalDateTime lastVisitedTime;
	private String kakaoId;
	private LocalDateTime createdTime;
	private Role role;

	public static MemberDto of(String kakaoId, String nickname, Role role) {
		return MemberDto.builder()
			.nickname(nickname)
			.kakaoId(kakaoId)
			.role(role)
			.build();
	}

	public static MemberDto from(MemberApiDto member) {
		return MemberDto.builder()
			.memberId(member.getMemberId())
			.nickname(member.getNickname())
			.lastVisitedTime(member.getLastVisitedTime())
			.kakaoId(member.getKakaoId())
			.createdTime(member.getCreatedTime())
			.role(member.getRole())
			.build();
	}

	public static MemberDto from(Member member) {
		return MemberDto.builder()
			.memberId(member.getMemberId())
			.nickname(member.getNickname())
			.lastVisitedTime(member.getLastVisitedTime())
			.kakaoId(member.getKakaoId())
			.createdTime(member.getCreatedTime())
			.role(member.getRole())
			.build();
	}

	public static MemberDto of(MemberApiDto.Request request, Long memberId) {
		return MemberDto.builder()
			.memberId(memberId)
			.nickname(request.getNickname())
			.build();
	}
}
