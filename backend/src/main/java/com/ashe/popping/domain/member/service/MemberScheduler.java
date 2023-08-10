package com.ashe.popping.domain.member.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ashe.popping.domain.favorite.service.FavoriteService;
import com.ashe.popping.domain.member.entity.Member;
import com.ashe.popping.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberScheduler {

	private final MemberRepository memberRepository;
	private final FavoriteService favoriteService;

	@Scheduled(cron = "0 0 5 * * *", zone = "Asia/Seoul")
	public void removeMember() {
		LocalDateTime now = LocalDateTime.now();
		List<Member> withdrawalMember = memberRepository.findByWithdrawalDateBefore(now);
		withdrawalMember.stream()
			.map(Member::getMemberId)
			.toList()
			.forEach(favoriteService::removeWithdrawalMember);
		int countDeletedMember = memberRepository.deleteByWithdrawalDateBefore(now);
		log.info("Number of members deleted after 30 days : {}", countDeletedMember);
	}

}
