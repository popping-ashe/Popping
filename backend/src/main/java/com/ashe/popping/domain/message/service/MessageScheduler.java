package com.ashe.popping.domain.message.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ashe.popping.domain.message.repository.MessageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageScheduler {
	private final MessageRepository messageRepository;

	@Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
	public void updateMessageState() {
		int countUpdatedMessages = messageRepository.updateMessageStateToExpired(LocalDateTime.now());
		log.info("Number of expired messages : {}", countUpdatedMessages);
	}
}
