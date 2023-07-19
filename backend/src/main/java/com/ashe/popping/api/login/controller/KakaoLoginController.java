package com.ashe.popping.api.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.login.service.KakaoLoginService;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class KakaoLoginController {
	private final KakaoLoginService kakaoTokenService;

	@GetMapping("/oauth/kakao/callback")
	public ResponseEntity<JwtTokenDto> loginCallback(String code) {
		JwtTokenDto jwtTokenDto = kakaoTokenService.kakaoLogin(code);
		return ResponseEntity.ok(jwtTokenDto);
	}
}