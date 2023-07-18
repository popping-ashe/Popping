package com.ashe.popping.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashe.popping.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByMemberId(Long memberId);

	Optional<Member> findByKakaoId(String kakaoId);
}
