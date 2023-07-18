package com.ashe.popping.domain.member.service;

import com.ashe.popping.api.member.dto.MemberDto;

public interface MemberService {
	MemberDto createMember(MemberDto memberDto);

	MemberDto getMemberByMemberId(Long memberId);

	MemberDto updateMember(MemberDto memberDto);

	void validateDuplicateMember(MemberDto memberDto);
}
