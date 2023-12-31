package com.ashe.popping.global.jwt.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.AuthenticationException;
import com.ashe.popping.global.jwt.contant.GrantType;
import com.ashe.popping.global.jwt.contant.TokenType;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;
import com.ashe.popping.global.redis.dto.RefreshToken;
import com.ashe.popping.global.redis.repository.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {

	// private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	// application.yml에 설정한 값을 가져온다.
	private final String accessTokenExpirationTime;
	private final String refreshTokenExpirationTime;
	private final String tokenSecret;

	public Date createAccessTokenExpireTime() {
		return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
	}

	public Date createRefreshTokenExpireTime() {
		return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
	}

	public String createAccessToken(Long memberId, Role role, Date expirationTime) {
		return createToken(TokenType.ACCESS.name(), memberId, role, expirationTime);
	}

	public String createRefreshToken(Long memberId, Role role, Date expirationTime) {
		String refreshToken = createToken(TokenType.REFRESH.name(), memberId, role, expirationTime);
		RefreshToken redisRefreshToken = new RefreshToken(memberId, refreshToken);
		refreshTokenRepository.save(redisRefreshToken);
		return refreshToken;
	}

	public String expireRefreshToken(Long memberId) {
		String refreshToken = createToken(TokenType.REFRESH.name(), memberId, Role.USER,
			new Date(System.currentTimeMillis()));
		RefreshToken redisRefreshToken = new RefreshToken(memberId, refreshToken);
		refreshTokenRepository.save(redisRefreshToken);
		return refreshToken;
	}

	public JwtTokenDto createJwtTokenDto(Long memberId, Role role) {
		Date accessTokenExpirationTime = createAccessTokenExpireTime();
		Date refreshTokenExpirationTime = createRefreshTokenExpireTime();
		String accessToken = createAccessToken(memberId, role, accessTokenExpirationTime);
		String refreshToken = createRefreshToken(memberId, role, refreshTokenExpirationTime);
		return JwtTokenDto.of(GrantType.BEARER.getType(), accessToken, accessTokenExpirationTime, refreshToken,
			refreshTokenExpirationTime);
	}

	public void validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			log.info("token 만료", e);
			throw new com.ashe.popping.global.error.exception.AuthenticationException(ErrorCode.TOKEN_EXPIRED);
		} catch (Exception e) {
			log.info("유효하지 않은 token", e);
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}

	}

	public Claims getTokenClaims(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(token).getBody();
		} catch (Exception e) {
			log.info("유효하지 않은 token", e);
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}
		return claims;
	}

	public String createToken(String tokenType, Long memberId, Role role, Date expirationTime) {
		return Jwts.builder()
			.setSubject(tokenType)
			.setIssuedAt(new Date())
			.setExpiration(expirationTime)
			.claim("role", role)
			.claim("memberId", memberId)
			.signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
			.setHeaderParam("typ", "JWT")
			.compact();
	}

}
