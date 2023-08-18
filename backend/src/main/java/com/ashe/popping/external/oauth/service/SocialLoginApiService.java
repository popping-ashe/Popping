package com.ashe.popping.external.oauth.service;

import com.ashe.popping.external.oauth.model.OAuthAttributes;

public interface SocialLoginApiService {
	OAuthAttributes getMemberInfo(String accessToken);
}
