package com.ashe.popping.domain.message.dto;

import java.time.LocalDateTime;

import com.ashe.popping.domain.message.entity.Message;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {

	private Integer state;

	private String content;

	private LocalDateTime createTime;

	private LocalDateTime expirationTime;

	private Long sender;

	private Long receiver;

	private String nickname;

	@Builder
	public MessageDto(Integer state, String content, LocalDateTime expirationTime, Long sender, Long receiver,
		String nickname) {
		this.state = state;
		this.content = content;
		this.expirationTime = expirationTime;
		this.sender = sender;
		this.receiver = receiver;
		this.nickname = nickname;
	}

	public static MessageDto from(Message message) {
		return MessageDto.builder()
			.state(message.getState())
			.content(message.getContent())
			.expirationTime(message.getExpirationTime())
			.sender(message.getSender())
			.receiver(message.getReceiver())
			.nickname(message.getNickname())
			.build();
	}
}
