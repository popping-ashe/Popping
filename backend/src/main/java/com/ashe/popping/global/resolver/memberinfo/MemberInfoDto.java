package com.ashe.popping.global.resolver.memberinfo;

import com.ashe.popping.domain.member.constant.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoDto {
	private Long memberId;
	private Role role;

	public static MemberInfoDto of(Long memberId, String role) {
		return MemberInfoDto.builder()
			.memberId(memberId)
			.role(Role.from(role))
			.build();
	}
}
