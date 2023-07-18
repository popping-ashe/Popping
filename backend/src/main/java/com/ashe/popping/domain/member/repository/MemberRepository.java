package com.ashe.popping.domain.member.repository;

import org.springframework.data.repository.CrudRepository;

import com.ashe.popping.domain.member.entity.Member;

public interface MemberRepository extends CrudRepository<Member, Long> {
	Member findByMemberId(Long memberId);
}
