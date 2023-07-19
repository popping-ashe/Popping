package com.ashe.popping.api.token.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ashe.popping.api.token.dto.AccessTokenResponseDto;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.AuthenticationException;
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

	public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken) {
		// 1. refreshToken으로 memberId 반환 받기
		Long memberId = 0L;
		try{
			Claims tokenClaims = tokenManager.getTokenClaims(refreshToken);
			memberId = Long.valueOf((Integer)tokenClaims.get("memberId"));
		}catch(Exception e){
			// 로그아웃 처리
			throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED); // 만료된 토큰
		}
		// 2. memberId로 refreshToken 레디스에서 가져오기
		Optional<RefreshToken> refreshTokenDto = refreshTokenRepository.findRefreshTokenByMemberId(memberId);
		System.out.println(refreshToken);
		// 3. client로부터 받은 refreshToken과 Redis에 저장된 refreshToken을 비교하기.
		// 받은 refreshToken과 비교
		if (refreshTokenDto.get().getRefreshToken().equals(refreshToken)) {
			MemberDto memberDto = memberService.getMemberByMemberId(memberId);
			System.out.println(memberDto);
			Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
			String accessToken = tokenManager.createAccessToken(memberId, memberDto.getRole(),
				accessTokenExpireTime);
			return AccessTokenResponseDto.of(accessToken, accessTokenExpireTime);
		} else {
			// 다른 경우
			// 악의적 이용 -> 로그아웃
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}

	}

}
