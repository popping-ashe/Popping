package com.ashe.popping.domain.message.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageCountDto {

	private Long receivedMessagesCount;
	private Long sentMessagesCount;
	private Long expiredMessagesCount;

	@Builder
	public MessageCountDto(Long receivedMessagesCount, Long sentMessagesCount, Long expiredMessagesCount) {
		this.receivedMessagesCount = receivedMessagesCount;
		this.sentMessagesCount = sentMessagesCount;
		this.expiredMessagesCount = expiredMessagesCount;
	}

	public static MessageCountDto of(Long receivedMessagesCount, Long sentMessagesCount, Long expiredMessagesCount){
		return MessageCountDto.builder()
			.receivedMessagesCount(receivedMessagesCount)
			.sentMessagesCount(sentMessagesCount)
			.expiredMessagesCount(expiredMessagesCount)
			.build();
	}
}


