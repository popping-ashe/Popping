package com.ashe.popping.api.logout.service;

import org.springframework.stereotype.Service;

import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.AuthenticationException;
import com.ashe.popping.global.jwt.contant.TokenType;
import com.ashe.popping.global.jwt.service.TokenManager;
import com.ashe.popping.global.redis.repository.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutService {
	private final TokenManager tokenManager;
	private final RefreshTokenRepository refreshTokenRepository;

	public void logoutByAccessToken(String accessToken) {
		// 1. 토큰 검증
		tokenManager.validateToken(accessToken);

		// 2. 토큰 타입 검증(ACCESS인지!)
		Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
		String tokenType = tokenClaims.getSubject();
		if (!TokenType.isAccessToken(tokenType)) {
			throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
		}

		// 3. refreshToken 만료 처리
		Long memberId = Long.valueOf((Integer)tokenClaims.get("memberId"));
		tokenManager.expireRefreshToken(memberId);
	}
}
