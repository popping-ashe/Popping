package com.ashe.popping.api.signout.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ashe.popping.api.signout.client.KakaoDisconnectClient;
import com.ashe.popping.api.signout.dto.KakaoDisconnectDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SignoutServiceImpl implements SignoutService{

	private final KakaoDisconnectClient kakaoDisconnectClient;

	@Value("${kakao.client.admin}")
	private String clientAdmin;

	public void disconnectKakao(String kakaoId){
		String contentType = "application/x-www-form-urlencoded";

		KakaoDisconnectDto.Request request = KakaoDisconnectDto.Request.of(kakaoId);
		KakaoDisconnectDto.Response response = kakaoDisconnectClient.disconnectKakao(contentType, "KakaoAK "+clientAdmin, request);
	}
}
