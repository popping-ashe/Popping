package com.ashe.popping.api.external.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoMemberInfoResponseDto {

	@JsonProperty("id")
	private String uuid;

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;


	@Getter
	@Setter
	public static class KakaoAccount{

		private Profile profile;
		@Getter
		@Setter
		public static class Profile{

			private String nickname;
		}

	}
}
