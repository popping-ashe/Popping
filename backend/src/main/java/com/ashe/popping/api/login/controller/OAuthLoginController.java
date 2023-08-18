package com.ashe.popping.api.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.login.service.OAuthLoginService;
import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.AuthenticationException;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OAuthLoginController {
	private final OAuthLoginService oAuthLoginService;
	@GetMapping("/oauth/{socialLoginType}/callback")
	public ResponseEntity<JwtTokenDto> loginCallback(String code, @PathVariable String socialLoginType) {
		if(socialLoginType.equals("kakao"))
			return ResponseEntity.ok(oAuthLoginService.socialLogin(code, MemberType.KAKAO));
		if(socialLoginType.equals("google"))
			return ResponseEntity.ok(oAuthLoginService.socialLogin(code, MemberType.GOOGLE));

		throw new AuthenticationException(ErrorCode.NOT_EXIST_CALLBACK_TYPE);
	}
}