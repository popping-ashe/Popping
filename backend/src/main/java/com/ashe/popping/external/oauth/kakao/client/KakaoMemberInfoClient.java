package com.ashe.popping.external.oauth.kakao.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ashe.popping.external.oauth.kakao.dto.KakaoMemberInfoResponseDto;

@FeignClient(url="https://kapi.kakao.com", contextId = "memberInfo", name="kakaoMemberInfoClient")
public interface KakaoMemberInfoClient {

	@GetMapping(value="/v2/user/me", consumes="application/json")
	KakaoMemberInfoResponseDto getKakaoMemberInfo(@RequestHeader("Content-type") String contentType, @RequestHeader("Authorization") String accessToken);
}
