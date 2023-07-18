package com.ashe.popping.external.oauth.kakao.service;

import com.ashe.popping.external.oauth.kakao.dto.KakaoMemberInfoResponseDto;

public interface KakaoLoginApiService {
	KakaoMemberInfoResponseDto getMemberInfo(String accessToken);
}
