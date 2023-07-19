package com.ashe.popping.api.message.dto;

import java.time.LocalDateTime;

import com.ashe.popping.domain.message.dto.MessageDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

public class MessageApiDto {
	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Request {
		private String content;
		private Long sender;
		private Long receiver;
		private String nickname;
		private Long retentionTime;

		@Builder
		public Request(String content, Long sender, Long receiver, String nickname, Long retentionTime) {
			this.content = content;
			this.sender = sender;
			this.receiver = receiver;
			this.nickname = nickname;
			this.retentionTime = retentionTime;
		}
	}

	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response {
		private Long messageId;
		private String content;
		private String state;
		private Long receiver;
		private String nickname;
		private LocalDateTime createTime;
		private LocalDateTime expirationTime;

		@Builder
		public Response(Long messageId, String content, String state, Long receiver, String nickname,
			LocalDateTime createTime,
			LocalDateTime expirationTime) {
			this.messageId = messageId;
			this.content = content;
			this.state = state;
			this.receiver = receiver;
			this.nickname = nickname;
			this.createTime = createTime;
			this.expirationTime = expirationTime;
		}

		public static Response from(MessageDto messageDto) {
			return Response.builder()
				.messageId(messageDto.getMessageId())
				.content(messageDto.getContent())
				.state(messageDto.getState().getMessage())
				.receiver(messageDto.getReceiver())
				.nickname(messageDto.getNickname())
				.createTime(messageDto.getCreateTime())
				.expirationTime(messageDto.getExpirationTime())
				.build();
		}
		
	}
}
