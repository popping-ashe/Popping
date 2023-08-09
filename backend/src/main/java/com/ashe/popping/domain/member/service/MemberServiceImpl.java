package com.ashe.popping.domain.member.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.entity.Member;
import com.ashe.popping.domain.member.repository.MemberRepository;
import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.AuthenticationException;
import com.ashe.popping.global.error.exception.BusinessException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	@Override
	public MemberDto getMemberByMemberId(Long memberId) {
		Optional<Member> member = memberRepository.findByMemberId(memberId);

		return MemberDto.from(member.get());
	}

	@Override
	public MemberDto updateNickname(MemberDto memberDto) {
		Member member = memberRepository.findByMemberId(memberDto.getMemberId()).get();

		member.updateNickname(memberDto.getNickname());

		return MemberDto.from(member);
	}

	@Override
	public MemberDto updateLastVisitedTime(MemberDto memberDto) {
		Member member = memberRepository.findByMemberId(memberDto.getMemberId()).get();

		member.updateLastVisitedTime(memberDto.getLastVisitedTime());

		return MemberDto.from(member);
	}

	@Override
	public void validateDuplicateMember(MemberDto memberDto) {
		Member member = Member.from(memberDto);
		Optional<Member> optionalMember = memberRepository.findBySocialLoginId(member.getSocialLoginId());
		if (optionalMember.isPresent()) {
			throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
		}
	}

	@Override
	public Optional<Member> getMemberBySocialId(String socialLoginId) {
		return memberRepository.findBySocialLoginId(socialLoginId);
	}

	@Override
	public MemberDto getMemberByShareId(Long shareId) {
		Optional<Member> member = memberRepository.findByShareId(shareId);
		if (member.isEmpty()) {
			throw new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER);
		}
		return MemberDto.from(member.get());
	}

	@Override
	public MemberDto createMember(MemberDto memberDto) {
		Member member = Member.from(memberDto);

		memberRepository.save(member);

		return MemberDto.from(member);
	}

	@Override
	public Long deleteMember(Long memberId) {
		long result = memberRepository.deleteByMemberId(memberId);
		if (result != 1) {
			throw new BusinessException(ErrorCode.NOT_EXIST_MEMBER);
		}
		return result;
	}

	@Override
	public MemberDto updateBio(MemberDto memberDto) {
		Member member = memberRepository.findByMemberId(memberDto.getMemberId()).get();
		member.updateBio(memberDto.getBio());
		return MemberDto.from(member);
	}

	@Override
	public List<MemberDto> getAllMember() {
		List<MemberDto> memberDtos = new LinkedList<>();
		memberRepository.findAll().forEach(m -> memberDtos.add(MemberDto.from(m)));
		return memberDtos;
	}
}
