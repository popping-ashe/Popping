package com.ashe.popping.domain.member.service;

import com.ashe.popping.api.member.dto.MemberDto;

public interface MemberService {
	MemberDto getMemberByMemberId(Long memberId);
}
