package com.ashe.popping.domain.member.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.domain.member.entity.Member;

import jakarta.transaction.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByMemberId(Long memberId);

	Optional<Member> findBySocialLoginIdAndMemberType(String Id, MemberType memberType);

	Optional<Member> findByShareId(Long shareId);

	Long deleteByMemberId(Long memberId);

	List<Member> findAll();

	@Modifying(clearAutomatically = true)
	@Transactional
	int deleteByWithdrawalDateBefore(LocalDateTime now);

	List<Member> findByWithdrawalDateBefore(LocalDateTime now);
}
