package com.ashe.popping.api.login.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
public class OAuthTokenDto {
	@Builder
	@Getter
	@ToString
	public static class Request {

		private final String grant_type = "authorization_code";

		private String client_id;

		private String client_secret;

		private String redirect_uri;

		private String code;

		public static Request of(String clientId, String clientSecret, String code, String redirectUri) {
			return Request.builder()
				.client_id(clientId)
				.client_secret(clientSecret)
				.redirect_uri(redirectUri)
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
	public static class Response {
		private String tokenType;
		private String accessToken;
		private Integer expiresIn;
		private String refreshToken;
		private Integer refreshTokenExpiresIn;
		private String scope;
	}
}
