package com.ashe.popping.api.token.dto;

import java.util.Date;

import com.ashe.popping.global.jwt.contant.GrantType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenResponseDto {
	private String grandType;
	private String accessToken;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date accessTokenExpireTime;

	public static AccessTokenResponseDto of(String accessToken, Date accessTokenExpireTime) {
		return AccessTokenResponseDto.builder()
			.grandType(GrantType.BEARER.getType())
			.accessToken(accessToken)
			.accessTokenExpireTime(accessTokenExpireTime)
			.build();
	}
}
