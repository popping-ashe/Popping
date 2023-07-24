package com.ashe.popping.domain.message.dto;

import java.time.LocalDateTime;

import com.ashe.popping.api.message.dto.MessageApiDto;
import com.ashe.popping.domain.message.entity.Message;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {
	private Long messageId;

	private MessageState state;

	private String content;

	private LocalDateTime createTime;

	private LocalDateTime expirationTime;

	private Long sender;

	private Long receiver;

	private String nickname;

	private String receiverNickname;

	@Builder
	public MessageDto(Long messageId, MessageState state, String content, LocalDateTime createTime,
		LocalDateTime expirationTime, Long sender, Long receiver, String nickname, String receiverNickname) {
		this.messageId = messageId;
		this.state = state;
		this.content = content;
		this.createTime = createTime;
		this.expirationTime = expirationTime;
		this.sender = sender;
		this.receiver = receiver;
		this.nickname = nickname;
		this.receiverNickname = receiverNickname;
	}

	public static MessageDto from(Message message) {
		return MessageDto.builder()
			.messageId(message.getMessageId())
			.state(message.getState())
			.content(message.getContent())
			.createTime(message.getCreateTime())
			.expirationTime(message.getExpirationTime())
			.sender(message.getSender())
			.receiver(message.getReceiver())
			.nickname(message.getNickname())
			.build();
	}

	public static MessageDto of(Message message, String receiverNickname) {
		return MessageDto.builder()
			.messageId(message.getMessageId())
			.state(message.getState())
			.content(message.getContent())
			.createTime(message.getCreateTime())
			.expirationTime(message.getExpirationTime())
			.sender(message.getSender())
			.receiver(message.getReceiver())
			.nickname(message.getNickname())
			.receiverNickname(receiverNickname)
			.build();
	}

	public static MessageDto from(MessageApiDto.Request request) {
		return MessageDto.builder()
			.state(MessageState.UNREAD)
			.content(request.getContent())
			.createTime(LocalDateTime.now())
			.expirationTime(LocalDateTime.now().plusHours(request.getRetentionTime()))
			.sender(request.getSender())
			.receiver(request.getReceiver())
			.nickname(request.getNickname())
			.build();
	}
}
