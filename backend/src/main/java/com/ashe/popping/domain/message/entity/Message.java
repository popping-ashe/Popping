package com.ashe.popping.domain.message.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.dto.MessageState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long messageId;

	@Enumerated(EnumType.STRING)
	private MessageState state;

	@Column(nullable = false)
	private String content;

	@CreatedDate
	private LocalDateTime createTime;

	@Column(nullable = false)
	private LocalDateTime expirationTime;

	private Long sender;

	@Column(nullable = false)
	private Long receiver;

	@Column(nullable = false, length = 20)
	private String nickname;

	@Column(nullable = false)
	private String replyAvailable;

	@Builder
	public Message(MessageState state, String content, LocalDateTime expirationTime, Long sender, Long receiver,
		String nickname, String replyAvailable) {
		this.state = state;
		this.content = content;
		this.expirationTime = expirationTime;
		this.sender = sender;
		this.receiver = receiver;
		this.nickname = nickname;
		this.replyAvailable = replyAvailable;
	}

	public static Message from(MessageDto messageDto) {
		return Message.builder()
			.state(messageDto.getState())
			.content(messageDto.getContent())
			.expirationTime(messageDto.getExpirationTime())
			.sender(messageDto.getSender())
			.receiver(messageDto.getReceiver())
			.nickname(messageDto.getNickname())
			.replyAvailable(messageDto.getReplyAvailable())
			.build();
	}

	public static Message of(MessageDto messageDto, MemberDto memberDto) {
		return Message.builder()
			.state(messageDto.getState())
			.content(messageDto.getContent())
			.expirationTime(messageDto.getExpirationTime())
			.sender(messageDto.getSender())
			.receiver(memberDto.getMemberId())
			.nickname(messageDto.getNickname())
			.replyAvailable(messageDto.getReplyAvailable())
			.build();
	}

	public static Message of(MessageDto messageDto, Long senderId, Long receiverId) {
		return Message.builder()
			.state(messageDto.getState())
			.content(messageDto.getContent())
			.expirationTime(messageDto.getExpirationTime())
			.sender(senderId)
			.receiver(receiverId)
			.nickname(messageDto.getNickname())
			.replyAvailable(messageDto.getReplyAvailable())
			.build();
	}

	public void updateStateToRead() {
		this.state = MessageState.READ;
	}
}
