package com.ashe.popping.domain.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashe.popping.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByMemberId(Long memberId);

	Optional<Member> findBySocialLoginId(String Id);

	Optional<Member> findByShareId(Long shareId);

	Long deleteByMemberId(Long memberId);

	List<Member> findAll();
}
