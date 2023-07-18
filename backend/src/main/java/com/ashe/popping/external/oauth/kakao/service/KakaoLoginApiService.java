package com.ashe.popping.external.oauth.kakao.service;

import com.ashe.popping.external.oauth.model.OAuthAttributes;

public interface KakaoLoginApiService {
	OAuthAttributes getMemberInfo(String accessToken);
}
