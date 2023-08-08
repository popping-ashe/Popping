package com.ashe.popping.external.oauth.model;

import com.ashe.popping.domain.member.constant.MemberType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {
	private String id;
	private String nickname;
	private MemberType memberType;
}