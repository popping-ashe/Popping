package com.ashe.popping.api.token.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.token.service.TokenService;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;
import com.ashe.popping.global.util.AuthorizationHeaderUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TokenController {
	private final TokenService tokenService;

	@PostMapping("/access-token/issue")
	public ResponseEntity<JwtTokenDto> createToken(HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
		String refreshToken = authorizationHeader.split(" ")[1];
		JwtTokenDto jwtTokenDto = tokenService.createTokenByRefreshToken(refreshToken);
		return ResponseEntity.ok(jwtTokenDto);

	}
}
