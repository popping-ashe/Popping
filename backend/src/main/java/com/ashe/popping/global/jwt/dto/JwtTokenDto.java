package com.ashe.popping.global.jwt.dto;

import java.util.Date;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenDto {

	private String grantType;
	private String accessToken;
	private Date accessTokenExpireTime;
	private String refreshToken;
	private Date refreshTokenExpireTime;
}
