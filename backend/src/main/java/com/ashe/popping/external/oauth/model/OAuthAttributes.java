package com.ashe.popping.external.oauth.model;

import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OAuthAttributes {
	private String kakaoId;
	private String nickname;
	

	public Member toMemberEntity(Role role){
		return Member.builder()
			.kakaoId(kakaoId)
			.nickname(nickname)
			.role(role)
			.build();
	}

}
