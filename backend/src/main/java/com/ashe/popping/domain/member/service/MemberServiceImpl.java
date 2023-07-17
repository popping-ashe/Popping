package com.ashe.popping.domain.member.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ashe.popping.domain.member.entity.Member;
import com.ashe.popping.domain.member.repository.MemberRepository;
import com.ashe.popping.api.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	@Override
	public MemberDto getMemberByMemberId(Long memberId) {
		
		Member member = memberRepository.findByMemberId(memberId);

		MemberDto memberDto = new ModelMapper().map(member, MemberDto.class);

		return memberDto;
	}
}
