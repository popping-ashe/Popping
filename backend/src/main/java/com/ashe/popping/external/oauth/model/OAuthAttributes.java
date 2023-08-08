package com.ashe.popping.external.oauth.model;

import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {
	private String id;
	private String nickname;
	private MemberType memberType;


	public static OAuthAttributes of(String id, String nickname, MemberType memberType){
		return OAuthAttributes.builder()
			.id(id)
			.nickname(nickname)
			.memberType(memberType)
			.build();
	}

	public Member toMemberEntity(MemberType memberType, Role role){
		return Member.builder()
			.memberType(memberType)
			.socialLoginId(id)
			.nickname(nickname)
			.role(role)
			.build();
	}
}