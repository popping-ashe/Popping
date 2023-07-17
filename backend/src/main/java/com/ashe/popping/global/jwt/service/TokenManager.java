package com.ashe.popping.global.jwt.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.AuthenticationException;
import com.ashe.popping.global.jwt.contant.GrantType;
import com.ashe.popping.global.jwt.contant.TokenType;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public class TokenManager {

	// application.yml에 설정한 값을 가져온다.
	private final String accessTokenExpirationTime;
	private final String refreshTokenExpirationTime;
	private final String tokenSecret;


	public Date createAccessTokenExpireTime(){
		return new Date(System.currentTimeMillis()+Long.parseLong(accessTokenExpirationTime));
	}

	public Date createRefreshTokenExpireTime(){
		return new Date(System.currentTimeMillis()+Long.parseLong(refreshTokenExpirationTime));
	}

	public String createAccessToken(Long memberId, Role role, Date expirationTime){
		String accessToken = Jwts.builder()
			.setSubject(TokenType.ACCESS.name())
			.setIssuedAt(new Date())
			.setExpiration(expirationTime)
			.claim("memberId", memberId)
			.claim("role", role)
			.signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
			.setHeaderParam("typ", "JWT")
			.compact();
		return accessToken;
	}

	public String createRefreshToken(Long memberId, Date expirationTime){
		String refreshToken = Jwts.builder()
			.setSubject(TokenType.REFRESH.name())
			.setIssuedAt(new Date())
			.setExpiration(expirationTime)
			.claim("memberId", memberId)
			.signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
			.setHeaderParam("typ", "JWT")
			.compact();

		return refreshToken;
	}

	public JwtTokenDto createJwtTokenDto(Long memberId, Role role){
		Date accessTokenExpirationTime = createAccessTokenExpireTime();
		Date refreshTokenExpirationTime = createRefreshTokenExpireTime();
		String accessToken = createAccessToken(memberId, role, accessTokenExpirationTime);
		String refreshToken = createRefreshToken(memberId, refreshTokenExpirationTime);
		return JwtTokenDto.builder()
			.grantType(GrantType.BEARER.getType())
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.accessTokenExpireTime(accessTokenExpirationTime)
			.refreshTokenExpireTime(refreshTokenExpirationTime)
			.build();
	}

	public void validateToken(String token){
		try{
			Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(token).getBody();
		}catch(ExpiredJwtException e){
			log.info("token 만료", e);
			throw new com.ashe.popping.global.error.exception.AuthenticationException(ErrorCode.TOKEN_EXPIRED);
		}catch(Exception e){
			log.info("유효하지 않은 token", e);
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}

	}

	public Claims getTokenClaims(String token){
		Claims claims;
		try{
			claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(token).getBody();
		} catch(Exception e){
			log.info("유효하지 않은 token", e);
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}
		return claims;
	}


}
