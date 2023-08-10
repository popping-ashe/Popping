package com.ashe.popping.domain.member.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.entity.Member;
import com.ashe.popping.domain.member.repository.MemberRepository;
import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.AuthenticationException;

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

		return MemberDto.from(member.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER)));
	}

	@Override
	public MemberDto updateNickname(MemberDto memberDto) {
		Optional<Member> member = memberRepository.findByMemberId(memberDto.getMemberId());

		if (member.isEmpty())
			throw new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER);

		member.get().updateNickname(memberDto.getNickname());

		return MemberDto.from(member.get());
	}

	@Override
	public MemberDto updateLastVisitedTime(MemberDto memberDto) {
		Optional<Member> member = memberRepository.findByMemberId(memberDto.getMemberId());

		if (member.isEmpty())
			throw new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER);

		member.get().updateLastVisitedTime(memberDto.getLastVisitedTime());

		return MemberDto.from(member.get());
	}

	@Override
	public Optional<MemberDto> getMemberBySocialLoginIdAndMemberType(String socialLoginId, MemberType memberType) {
		Optional<Member> optionalMember = memberRepository.findBySocialLoginIdAndMemberType(socialLoginId,
			memberType);
		return optionalMember.map(MemberDto::from).or(() -> Optional.ofNullable(null));
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
			throw new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER);
		}
		return result;
	}

	@Override
	public MemberDto updateBio(MemberDto memberDto) {
		Optional<Member> member = memberRepository.findByMemberId(memberDto.getMemberId());
		if (member.isEmpty())
			throw new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER);

		member.get().updateBio(memberDto.getBio());
		return MemberDto.from(member.get());
	}

	@Override
	public List<MemberDto> getAllMember() {
		List<MemberDto> memberDtos = new LinkedList<>();
		memberRepository.findAll().forEach(m -> memberDtos.add(MemberDto.from(m)));
		return memberDtos;
	}
}
