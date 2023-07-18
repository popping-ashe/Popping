package com.ashe.popping.domain.member.service;

import java.util.Optional;

import com.ashe.popping.api.member.dto.MemberDto;
import com.ashe.popping.domain.member.entity.Member;

public interface MemberService {
	MemberDto createMember(MemberDto memberDto);

	MemberDto getMemberByMemberId(Long memberId);

	MemberDto updateMember(MemberDto memberDto);

	void validateDuplicateMember(MemberDto memberDto);

	Optional<Member> getMemberByKakaoId(String kakaoId);
}
