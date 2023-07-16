package com.ashe.popping.api.token.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import feign.form.FormProperty;
import lombok.*;

@Component
public class KakaoTokenDto {
	@Builder
	@Getter
	@ToString
	public static class Request{

		@FormProperty("grant_type")
		private final String grant_type = "authorization_code";

		@FormProperty("client_id")
		private String client_id;

		@FormProperty("client_secret")
		private String client_secret;

		@FormProperty("redirect_uri")
		private final String redirect_uri = "http://localhost:8080/oauth/kakao/callback";

		private String code;

		public static Request of(String clientId, String clientSecret, String code){
			return Request.builder()
						.client_id(clientId)
						.client_secret(clientSecret)
						.code(code)
						.build();
		}

	}

	@Builder
	@Getter
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response{
		private String tokenType;
		private String accessToken;
		private Integer expiresIn;
		private String refreshToken;
		private Integer refreshTokenExpiresIn;
		private String scope;
	}
}
