package com.ashe.popping.external.oauth.google.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ashe.popping.external.oauth.google.dto.GoogleMemberInfoResponseDto;

@FeignClient(url="https://www.googleapis.com", value="googleMemberInfoClient")
public interface GoogleMemberInfoClient {

	@GetMapping("/userinfo/v2/me")
	GoogleMemberInfoResponseDto getGoogleMemberInfo(@RequestHeader("Authorization") String accessToken);
}
