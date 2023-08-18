package com.ashe.popping.external.oauth.kakao.service;

import org.springframework.stereotype.Service;

import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.external.oauth.kakao.client.KakaoMemberInfoClient;
import com.ashe.popping.external.oauth.kakao.dto.KakaoMemberInfoResponseDto;
import com.ashe.popping.external.oauth.model.OAuthAttributes;
import com.ashe.popping.external.oauth.service.SocialLoginApiService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginApiServiceImpl implements SocialLoginApiService{
	private final KakaoMemberInfoClient kakaoMemberInfoClient;
	private final String contentType = "application/x-www-form-urlencoded;charset=utf-8";

	public OAuthAttributes getMemberInfo(String accessToken){
		KakaoMemberInfoResponseDto kakaoMemberInfoResponseDto = kakaoMemberInfoClient.getKakaoMemberInfo(contentType, accessToken);
		return OAuthAttributes.of(kakaoMemberInfoResponseDto.getKakaoId(), kakaoMemberInfoResponseDto.getKakaoAccount().getProfile().getNickname(), MemberType.KAKAO);
	}

}
