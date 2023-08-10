package com.ashe.popping.domain.member.service;

import java.util.List;
import java.util.Optional;

import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.domain.member.dto.MemberDto;

public interface MemberService {
	MemberDto createMember(MemberDto memberDto);

	MemberDto getMemberByMemberId(Long memberId);

	MemberDto updateNickname(MemberDto memberDto);

	MemberDto updateLastVisitedTime(MemberDto memberDto);

	void validateDuplicateMember(MemberDto memberDto);

	Optional<MemberDto> getMemberBySocialLoginIdAndMemberType(String socialLoginId, MemberType memberType);

	MemberDto getMemberByShareId(Long shareId);

	Long deleteMember(Long memberId);

	MemberDto updateBio(MemberDto memberDto);

	List<MemberDto> getAllMember();
}
