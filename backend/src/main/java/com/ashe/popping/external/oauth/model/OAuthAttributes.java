package com.ashe.popping.external.oauth.model;

import lombok.ToString;

@ToString
public class OAuthAttributes {
	private String kakaoId;
	private String nickname;
	
	
	// 멤버 엔티티 빌드 패턴 추가 후 함수 생성
	public OAuthAttributes of(String kakaoId, String nickname){
		this.kakaoId = kakaoId; this.nickname = nickname;
		return null;
	}

}
