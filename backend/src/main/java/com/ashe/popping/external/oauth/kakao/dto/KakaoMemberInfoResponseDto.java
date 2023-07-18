package com.ashe.popping.external.oauth.kakao.dto;

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
	public static class KakaoAccount{

		private Profile profile;
		@Getter
		@Setter
		@ToString
		public static class Profile{

			private String nickname;
		}

	}
}
