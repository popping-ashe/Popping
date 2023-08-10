package com.ashe.popping.domain.message.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageRedisDto {

	@Id
	private Long messageId;

	private LocalDateTime expirationTime;

	@Builder
	public MessageRedisDto(Long messageId, LocalDateTime expirationTime) {
		this.messageId = messageId;
		this.expirationTime = expirationTime;
	}

	public static MessageRedisDto of(Long messageId, LocalDateTime expirationTime) {
		return MessageRedisDto.builder()
			.messageId(messageId)
			.expirationTime(expirationTime)
			.build();
	}

}
