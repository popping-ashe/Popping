package com.ashe.popping.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ashe.popping.global.jwt.service.TokenManager;
import com.ashe.popping.global.redis.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	private final RefreshTokenRepository refreshTokenRepository;
	@Value("${token.access-token-expiration-time}")
	private String accessTokenExpirationTime;

	@Value("${token.refresh-token-expiration-time}")
	private String refreshTokenExpirationTime;

	@Value("${token.secret}")
	private String tokenSecret;

	@Bean
	public TokenManager tokenManager() {
		return new TokenManager(refreshTokenRepository, accessTokenExpirationTime, refreshTokenExpirationTime,
			tokenSecret);
	}
}
