package com.ashe.popping.api.token.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.AuthenticationException;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;
import com.ashe.popping.global.jwt.service.TokenManager;
import com.ashe.popping.global.redis.dto.RefreshToken;
import com.ashe.popping.global.redis.repository.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {
	private final MemberService memberService;
	private final TokenManager tokenManager;
	private final RefreshTokenRepository refreshTokenRepository;

	public JwtTokenDto createTokenByRefreshToken(String refreshToken) {
		// 1. refreshToken으로 memberId 반환 받기
		try {
			Claims tokenClaims = tokenManager.getTokenClaims(refreshToken);
			Long memberId = Long.valueOf((Integer)tokenClaims.get("memberId"));
			// 2. memberId로 refreshToken 레디스에서 가져오기
			Optional<RefreshToken> refreshTokenDto = refreshTokenRepository.findRefreshTokenByMemberId(memberId);
			// 3. client로부터 받은 refreshToken과 Redis에 저장된 refreshToken을 비교하기.
			// 받은 refreshToken과 비교
			if (!(refreshTokenDto.isEmpty()) && refreshTokenDto.get().getRefreshToken().equals(refreshToken)) {
				MemberDto memberDto = memberService.getMemberByMemberId(memberId);
				Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
				String accessToken = tokenManager.createAccessToken(memberId, memberDto.getRole(),
					accessTokenExpireTime);
				return tokenManager.createJwtTokenDto(memberDto.getMemberId(), memberDto.getRole());
			} else {
				// 다른 경우
				tokenManager.expireRefreshToken(memberId);
				throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
			}
		} catch (Exception e) {
			// refreshToken 만료 처리하기!!
			throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED); // 만료된 토큰
		}

	}

}
