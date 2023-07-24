package com.ashe.popping.api.token.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.token.service.TokenService;
import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.AuthenticationException;
import com.ashe.popping.global.jwt.contant.TokenType;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;
import com.ashe.popping.global.jwt.service.TokenManager;
import com.ashe.popping.global.util.AuthorizationHeaderUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TokenController {
	private final TokenService tokenService;
	private final TokenManager tokenManager;

	@GetMapping("/access-token/issue")
	public ResponseEntity<JwtTokenDto> createToken(HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
		String refreshToken = authorizationHeader.split(" ")[1];
		Claims tokenClaims = tokenManager.getTokenClaims(refreshToken);
		String tokenType = tokenClaims.getSubject();
		if (!TokenType.isRefreshToken(tokenType)) {
			throw new AuthenticationException(ErrorCode.NOT_REFRESH_TOKEN_TYPE);
		}
		JwtTokenDto jwtTokenDto = tokenService.createTokenByRefreshToken(refreshToken);
		return ResponseEntity.ok(jwtTokenDto);

	}
}
