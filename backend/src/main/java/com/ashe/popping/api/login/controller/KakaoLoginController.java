package com.ashe.popping.api.login.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ashe.popping.api.login.service.KakaoLoginService;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;

@Controller
@RequiredArgsConstructor
public class KakaoLoginController {
	private final KakaoLoginService kakaoTokenService;

	@GetMapping("/oauth/kakao/callback")
	public @ResponseBody String loginCallback(String code) {
		JwtTokenDto jwtTokenDto = kakaoTokenService.kakaoLogin(code);
		return "JwtToken : "+jwtTokenDto;
	}
}