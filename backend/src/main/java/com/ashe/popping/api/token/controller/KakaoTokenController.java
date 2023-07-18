package com.ashe.popping.api.token.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ashe.popping.external.oauth.kakao.client.KakaoMemberInfoClient;
import com.ashe.popping.external.oauth.kakao.dto.KakaoMemberInfoResponseDto;
import com.ashe.popping.api.token.client.KakaoTokenClient;
import com.ashe.popping.api.token.dto.KakaoTokenDto;
import com.ashe.popping.external.oauth.kakao.service.KakaoLoginApiService;
import com.ashe.popping.external.oauth.model.OAuthAttributes;
import com.ashe.popping.global.jwt.contant.GrantType;

@Controller
@RequiredArgsConstructor
public class KakaoTokenController {
	private final KakaoTokenClient kakaoTokenClient;
	private final KakaoMemberInfoClient kakaoMemberInfoClient;
	private final KakaoLoginApiService kakaoLoginApiService;

	@Value("${kakao.client.id}")
	private String clientId;

	@Value("${kakao.client.secret}")
	private String clientSecret;

	@GetMapping("/oauth/kakao/callback")
	public @ResponseBody String loginCallback(String code) {
		String contentType="application/x-www-form-urlencoded;charset=utf-8";
		KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.of(clientId, clientSecret, code);
		KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
		OAuthAttributes kakaoMemberInfoResponseDto = kakaoLoginApiService.getMemberInfo(GrantType.BEARER.getType()+" "+kakaoToken.getAccessToken());
		return "kakao token : "+kakaoToken+", "+kakaoMemberInfoResponseDto;
	}
}