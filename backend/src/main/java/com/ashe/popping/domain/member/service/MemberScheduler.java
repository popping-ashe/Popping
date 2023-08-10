package com.ashe.popping.domain.member.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ashe.popping.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberScheduler {

	private final MemberRepository memberRepository;

	@Scheduled(cron = "0 0 5 * * *", zone = "Asia/Seoul")
	public void removeMember() {
		int countDeletedMember = memberRepository.deleteByWithdrawalDateBefore(LocalDateTime.now());
		log.info("Number of members deleted after 30 days : {}", countDeletedMember);
	}

}
