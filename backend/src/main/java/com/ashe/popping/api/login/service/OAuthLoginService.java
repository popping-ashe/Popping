package com.ashe.popping.api.login.service;

import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;

public interface OAuthLoginService {
	JwtTokenDto socialLogin(String code, MemberType memberType);
}
