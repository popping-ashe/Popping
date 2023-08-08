package com.ashe.popping.api.signout.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ashe.popping.api.signout.dto.KakaoDisconnectDto;


@FeignClient(url = "https://kapi.kakao.com", name="kakaoDisconnectClient")
public interface KakaoDisconnectClient {

	@PostMapping(value = "/v1/user/unlink", consumes="application/json")
	KakaoDisconnectDto.Response disconnectKakao(@RequestHeader("Content-type") String contentType,
		@RequestHeader("Authorization") String adminKey,
		@SpringQueryMap KakaoDisconnectDto.Request request);
}