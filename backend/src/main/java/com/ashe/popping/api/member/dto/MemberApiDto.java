package com.ashe.popping.api.member.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ashe.popping.api.termsagreement.dto.TermsAgreementApiDto;
import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class MemberApiDto {
	private Long memberId;
	private String nickname;
	private LocalDateTime lastVisitedTime;
	private String socialLoginId;
	private MemberType memberType;
	private LocalDateTime createdTime;
	private Role role;
	private String bio;

	public static MemberApiDto of(String socialLoginId, String nickname, Role role, MemberType memberType) {
		return MemberApiDto.builder()
			.nickname(nickname)
			.memberType(memberType)
			.socialLoginId(socialLoginId)
			.role(role)
			.build();
	}

	public static MemberApiDto of(MemberApiDto.Request request, Long memberId) {
		return MemberApiDto.builder()
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

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class UpdateBioRequest {
		private String bio;

		@Builder
		public UpdateBioRequest(String bio) {
			this.bio = bio;
		}
	}

	@Builder
	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class Response {
		private Long memberId;
		private String nickname;
		private Long expiredMessageCount;
		private String bio;
		private List<TermsAgreementApiDto.Response> termsAgreement;

		public static Response from(MemberDto memberDto) {
			return Response.builder()
				.memberId(memberDto.getMemberId())
				.nickname(memberDto.getNickname())
				.bio(memberDto.getBio())
				.build();
		}

		public static Response of(MemberDto memberDto, Long expiredMessageCount) {
			return Response.builder()
				.memberId(memberDto.getMemberId())
				.nickname(memberDto.getNickname())
				.bio(memberDto.getBio())
				.expiredMessageCount(expiredMessageCount)
				.build();
		}

		public static Response of(MemberDto memberDto, Long expiredMessageCount,
			List<TermsAgreementApiDto.Response> termsAgreement) {
			return Response.builder()
				.memberId(memberDto.getMemberId())
				.nickname(memberDto.getNickname())
				.bio(memberDto.getBio())
				.expiredMessageCount(expiredMessageCount)
				.termsAgreement(termsAgreement)
				.build();
		}

	}
}
