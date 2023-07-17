package com.ashe.popping.api.external.oauth.model;

import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.entity.Member;

public class OAuthAttributes {
	private String uuid;
	private String nickname;
	
	
	// 멤버 엔티티 빌드 패턴 추가 후 함수 생성
	public OAuthAttributes of(String uuid, String nickname){
		this.uuid = uuid; this.nickname = nickname;
		return null;
	}

}
