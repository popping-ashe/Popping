package com.ashe.popping.api.login.kakao.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ashe.popping.api.login.dto.OAuthTokenDto;

@FeignClient(url = "https://kauth.kakao.com", contextId = "acessToken", name="kakaoTokenClient")
public interface KakaoTokenClient {

	@PostMapping(value = "/oauth/token", consumes="application/json")
	OAuthTokenDto.Response requestKakaoToken(@RequestHeader("Content-type") String contentType,
											 @SpringQueryMap OAuthTokenDto.Request request);
}
