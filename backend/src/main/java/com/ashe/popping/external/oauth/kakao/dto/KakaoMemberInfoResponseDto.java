package com.ashe.popping.external.oauth.kakao.dto;

import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoMemberInfoResponseDto {

	@JsonProperty("id")
	private String kakaoId;

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Getter
	@Setter
	@ToString
	public static class KakaoAccount {

		private Profile profile;

		@Getter
		@Setter
		@ToString
		public static class Profile {

			private String nickname;
		}

	}

	public MemberDto toMemberDto(Role role, Long shareId) {
		return MemberDto.of(kakaoId, kakaoAccount.getProfile().getNickname(), role, shareId, MemberType.KAKAO);
	}

}
