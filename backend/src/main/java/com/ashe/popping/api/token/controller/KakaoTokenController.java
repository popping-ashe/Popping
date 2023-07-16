package com.ashe.popping.api.token.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ashe.popping.api.token.client.KakaoTokenClient;
import com.ashe.popping.api.token.dto.KakaoTokenDto;

@Controller
@RequiredArgsConstructor
public class KakaoTokenController {
	private final KakaoTokenClient kakaoTokenClient;

	@Value("${kakao.client.id}")
	private String clientId;

	@Value("${kakao.client.secret}")
	private String clientSecret;

	@GetMapping("/oauth/kakao/callback")
	public @ResponseBody String loginCallback(String code) {
		String contentType="application/x-www-form-urlencoded;charset=utf-8";
		KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.of(clientId, clientSecret, code);
		KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
		System.out.println(kakaoToken);
		return "kakao token : "+kakaoToken;
	}
}