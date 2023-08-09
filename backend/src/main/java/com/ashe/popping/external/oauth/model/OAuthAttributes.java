package com.ashe.popping.external.oauth.model;

import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.dto.MemberDto;
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

	public MemberDto toMemberDto(MemberType memberType, Role role, Long shareId){
		return MemberDto.of(id, nickname, role, shareId, memberType);
	}
}