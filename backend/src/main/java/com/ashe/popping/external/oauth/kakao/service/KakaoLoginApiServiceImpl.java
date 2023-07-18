package com.ashe.popping.external.oauth.kakao.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ashe.popping.api.member.dto.MemberDto;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.entity.Member;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.external.oauth.kakao.client.KakaoMemberInfoClient;
import com.ashe.popping.external.oauth.kakao.dto.KakaoMemberInfoResponseDto;
import com.ashe.popping.external.oauth.model.OAuthAttributes;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;
import com.ashe.popping.global.jwt.service.TokenManager;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginApiServiceImpl implements KakaoLoginApiService{
	private final KakaoMemberInfoClient kakaoMemberInfoClient;
	private final TokenManager tokenManager;
	private final MemberService memberService;
	private final String contentType = "application/x-www-form-urlencoded;charset=utf-8";

	public OAuthAttributes getMemberInfo(String settingAccessToken){

		KakaoMemberInfoResponseDto kakaoMemberInfoResponseDto = kakaoMemberInfoClient.getKakaoMemberInfo(contentType, settingAccessToken);
		KakaoMemberInfoResponseDto.KakaoAccount kakaoAccount = kakaoMemberInfoResponseDto.getKakaoAccount();
		OAuthAttributes memberInfo = OAuthAttributes.of(kakaoMemberInfoResponseDto.getKakaoId(), kakaoAccount.getProfile().getNickname());
		log.info("memberInfo : {}", memberInfo);
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
		return OAuthAttributes.of(kakaoMemberInfoResponseDto.getKakaoId(), kakaoAccount.getProfile().getNickname());
	}

}
