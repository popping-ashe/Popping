package com.ashe.popping.api.login.google.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ashe.popping.api.login.dto.OAuthTokenDto;

@FeignClient(url = "https://oauth2.googleapis.com", name="googleTokenClient")
public interface GoogleTokenClient {

	@PostMapping(value = "/token", consumes="application/json")
	OAuthTokenDto.Response requestGoogleToken(@RequestHeader("Content-type") String contentType,
											 @SpringQueryMap OAuthTokenDto.Request request);
}
