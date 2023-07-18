package com.ashe.popping.domain.member.dto;

import java.time.LocalDateTime;

import com.ashe.popping.api.member.dto.MemberApiDto;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.entity.Member;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class MemberDto {
	private Long memberId;
	private String nickname;
	private LocalDateTime lastVisitedTime;
	private String kakaoId;
	private LocalDateTime createdTime;
	private Role role;

	public static MemberDto from(Request request) {
		return MemberDto.builder()
			.nickname(request.getNickname())
			.build();
	}

	public static MemberDto of(String kakaoId, String nickname, Role role) {
		return MemberDto.builder()
			.nickname(nickname)
			.kakaoId(kakaoId)
			.role(role)
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

	public static MemberDto from(MemberApiDto memberApiDto) {
		return MemberDto.builder()
			.memberId(memberApiDto.getMemberId())
			.nickname(memberApiDto.getNickname())
			.lastVisitedTime(memberApiDto.getLastVisitedTime())
			.kakaoId(memberApiDto.getKakaoId())
			.createdTime(memberApiDto.getCreatedTime())
			.role(memberApiDto.getRole())
			.build();
	}

	public static MemberDto of(Request request, Long memberId) {
		return MemberDto.builder()
			.memberId(memberId)
			.nickname(request.getNickname())
			.build();
	}

	@Builder
	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class Request {
		private String nickname;

	}

	@Builder
	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class Response {
		private Long memberId;
		private String nickname;

		public static Response from(MemberDto memberDto) {
			return Response.builder()
				.memberId(memberDto.getMemberId())
				.nickname(memberDto.getNickname())
				.build();
		}

	}
}
