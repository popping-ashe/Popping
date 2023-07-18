package com.ashe.popping.external.oauth.model;

import com.ashe.popping.api.member.dto.MemberDto;
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

	public static OAuthAttributes of(String kakaoId, String nickname){
		return OAuthAttributes.builder()
			.kakaoId(kakaoId)
			.nickname(nickname)
			.build();
	}

	public MemberDto toMemberDto(Role role){
		return MemberDto.of(kakaoId, nickname, role);
	}

}
