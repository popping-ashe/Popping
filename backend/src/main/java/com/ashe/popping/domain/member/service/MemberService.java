package com.ashe.popping.domain.member.service;

import java.util.List;
import java.util.Optional;

import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.entity.Member;

public interface MemberService {
	MemberDto createMember(MemberDto memberDto);

	MemberDto getMemberByMemberId(Long memberId);

	MemberDto updateNickname(MemberDto memberDto);

	MemberDto updateLastVisitedTime(MemberDto memberDto);

	void validateDuplicateMember(MemberDto memberDto);

	Optional<Member> getMemberBySocialId(String kakaoId);

	MemberDto getMemberByShareId(Long shareId);

	Long deleteMember(Long memberId);

	MemberDto updateBio(MemberDto memberDto);
	
	List<MemberDto> getAllMember();
}
