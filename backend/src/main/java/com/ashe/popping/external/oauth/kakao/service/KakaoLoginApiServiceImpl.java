package com.ashe.popping.external.oauth.kakao.service;

import org.springframework.stereotype.Service;

import com.ashe.popping.external.oauth.kakao.client.KakaoMemberInfoClient;
import com.ashe.popping.external.oauth.kakao.dto.KakaoMemberInfoResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginApiServiceImpl implements KakaoLoginApiService{
	private final KakaoMemberInfoClient kakaoMemberInfoClient;
	private final String contentType = "application/x-www-form-urlencoded;charset=utf-8";

	public KakaoMemberInfoResponseDto getMemberInfo(String settingAccessToken){
		return kakaoMemberInfoClient.getKakaoMemberInfo(contentType, settingAccessToken);
	}

}