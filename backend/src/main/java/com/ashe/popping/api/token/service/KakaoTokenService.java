package com.ashe.popping.api.token.service;

import com.ashe.popping.global.jwt.dto.JwtTokenDto;

public interface KakaoTokenService {
	JwtTokenDto kakaoLogin(String code);
}
