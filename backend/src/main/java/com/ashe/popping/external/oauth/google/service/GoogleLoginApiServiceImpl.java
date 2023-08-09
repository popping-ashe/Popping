package com.ashe.popping.external.oauth.google.service;

import org.springframework.stereotype.Service;

import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.external.oauth.google.client.GoogleMemberInfoClient;
import com.ashe.popping.external.oauth.google.dto.GoogleMemberInfoResponseDto;
import com.ashe.popping.external.oauth.model.OAuthAttributes;
import com.ashe.popping.external.oauth.service.SocialLoginApiService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GoogleLoginApiServiceImpl implements SocialLoginApiService {

	private final GoogleMemberInfoClient googleMemberInfoClient;

	public OAuthAttributes getMemberInfo(String accessToken){
		GoogleMemberInfoResponseDto googleMemberInfoResponseDto = googleMemberInfoClient.getGoogleMemberInfo(accessToken);
		return OAuthAttributes.of(googleMemberInfoResponseDto.getId(), googleMemberInfoResponseDto.getNickname(), MemberType.GOOGLE);
	}
}
