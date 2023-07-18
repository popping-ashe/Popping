package com.ashe.popping.api.token.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ashe.popping.api.member.dto.MemberDto;
import com.ashe.popping.api.token.client.KakaoTokenClient;
import com.ashe.popping.api.token.dto.KakaoTokenDto;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.entity.Member;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.external.oauth.kakao.client.KakaoMemberInfoClient;
import com.ashe.popping.external.oauth.kakao.dto.KakaoMemberInfoResponseDto;
import com.ashe.popping.external.oauth.kakao.service.KakaoLoginApiService;
import com.ashe.popping.global.jwt.contant.GrantType;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;
import com.ashe.popping.global.jwt.service.TokenManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoTokenServiceImpl implements KakaoTokenService{
	private final KakaoTokenClient kakaoTokenClient;
	private final KakaoMemberInfoClient kakaoMemberInfoClient;
	private final KakaoLoginApiService kakaoLoginApiService;
	private final TokenManager tokenManager;
	private final MemberService memberService;

	@Value("${kakao.client.id}")
	private String clientId;

	@Value("${kakao.client.secret}")
	private String clientSecret;

	@Override
	public JwtTokenDto kakaoLogin(String code) {
		String contentType="application/x-www-form-urlencoded;charset=utf-8";
		KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.of(clientId, clientSecret, code);
		KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
		KakaoMemberInfoResponseDto memberInfo = kakaoLoginApiService.getMemberInfo(GrantType.BEARER.getType()+" "+kakaoToken.getAccessToken());
		JwtTokenDto jwtTokenDto;
		Optional<Member> optionalMember = memberService.getMemberByKakaoId(memberInfo.getKakaoId());

		// 1. 신규 회원
		if(optionalMember.isEmpty()){
			MemberDto oauthMember = memberInfo.toMemberDto(Role.USER);
			oauthMember = memberService.createMember(oauthMember);
			// 토큰 생성
			jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
		}
		// 2. 가입된 회원
		else{
			Member oauthMember = optionalMember.get();
			// 토큰 생성
			jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
		}
		return jwtTokenDto;
	}
}
