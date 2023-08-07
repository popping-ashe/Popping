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

	private String replyAvailable;

	@Builder
	public MessageDto(Long messageId, MessageState state, String content, LocalDateTime createTime,
		LocalDateTime expirationTime, Long sender, Long receiver, String nickname, String receiverNickname,
		String replyAvailable) {
		this.messageId = messageId;
		this.state = state;
		this.content = content;
		this.createTime = createTime;
		this.expirationTime = expirationTime;
		this.sender = sender;
		this.receiver = receiver;
		this.nickname = nickname;
		this.receiverNickname = receiverNickname;
		this.replyAvailable = replyAvailable;
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
			.replyAvailable(message.getReplyAvailable())
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
			.replyAvailable(message.getReplyAvailable())
			.build();
	}

	public static MessageDto from(MessageApiDto.Request request) {
		return MessageDto.builder()
			.state(MessageState.UNREAD)
			.content(request.getContent())
			.createTime(LocalDateTime.now())
			.expirationTime(LocalDateTime.now().plusHours(request.getRetentionTime()))
			.sender(request.getSender())
			.receiver(request.getShareId())
			.nickname(request.getNickname())
			.replyAvailable(request.getReplyAvailable())
			.build();
	}

	public static MessageDto from(MessageApiDto.ReplyRequest request) {
		return MessageDto.builder()
			.state(MessageState.UNREAD)
			.content(request.getContent())
			.createTime(LocalDateTime.now())
			.messageId(request.getMessageId())
			.expirationTime(LocalDateTime.now().plusHours(request.getRetentionTime()))
			.nickname(request.getNickname())
			.replyAvailable(request.getReplyAvailable())
			.build();
	}
}
