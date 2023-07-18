package com.ashe.popping.api.login.service;

import com.ashe.popping.global.jwt.dto.JwtTokenDto;

public interface KakaoLoginService {
	JwtTokenDto kakaoLogin(String code);
}
