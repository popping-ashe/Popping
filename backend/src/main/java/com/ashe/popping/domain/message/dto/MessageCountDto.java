package com.ashe.popping.domain.message.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageCountDto {

	private Long receivedMessagesCount;
	private Long sentMessagesCount;
	private Long unreadMessagesCount;

	@Builder
	public MessageCountDto(Long receivedMessagesCount, Long sentMessagesCount, Long unreadMessagesCount) {
		this.receivedMessagesCount = receivedMessagesCount;
		this.sentMessagesCount = sentMessagesCount;
		this.unreadMessagesCount = unreadMessagesCount;
	}

	public static MessageCountDto of(Long receivedMessagesCount, Long sentMessagesCount, Long unreadMessagesCount){
		return MessageCountDto.builder()
			.receivedMessagesCount(receivedMessagesCount)
			.sentMessagesCount(sentMessagesCount)
			.unreadMessagesCount(unreadMessagesCount)
			.build();
	}
}


