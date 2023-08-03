package com.ashe.popping.api.share.dto;

import java.time.LocalDateTime;

import com.ashe.popping.domain.message.dto.MessageDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

public class ShareApiDto {
	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class ShareResponse {
		private Long shareId;

		@Builder
		public ShareResponse(Long shareId) {
			this.shareId = shareId;
		}
	}

	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class MessageResponse {
		private Long messageId;
		private String state;
		private Long receiver;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime createTime;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime expirationTime;

		@Builder
		public MessageResponse(Long messageId, String state, Long receiver,
			LocalDateTime createTime,
			LocalDateTime expirationTime) {
			this.messageId = messageId;
			this.state = state;
			this.receiver = receiver;
			this.createTime = createTime;
			this.expirationTime = expirationTime;
		}

		public static MessageResponse from(MessageDto messageDto) {
			return MessageResponse.builder()
				.messageId(messageDto.getMessageId())
				.state(messageDto.getState().getMessage())
				.receiver(messageDto.getReceiver())
				.createTime(messageDto.getCreateTime())
				.expirationTime(messageDto.getExpirationTime())
				.build();
		}

	}
}
