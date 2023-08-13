package com.ashe.popping.api.message.dto;

import java.time.LocalDateTime;

import com.ashe.popping.domain.message.dto.MessageCountDto;
import com.ashe.popping.domain.message.dto.MessageDto;
import com.fasterxml.jackson.annotation.JsonFormat;
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
		private Long shareId;
		private String nickname;
		private Long retentionTime;
		private String replyAvailable;

		@Builder
		public Request(String content, Long sender, Long shareId, String nickname, Long retentionTime,
			String replyAvailable) {
			this.content = content;
			this.sender = sender;
			this.shareId = shareId;
			this.nickname = nickname;
			this.retentionTime = retentionTime;
			this.replyAvailable = replyAvailable;
		}

		public static Request of(String content, String nickname, Long retentionTime, String replyAvailable) {
			return Request.builder()
				.content(content)
				.nickname(nickname)
				.retentionTime(retentionTime)
				.replyAvailable(replyAvailable)
				.build();
		}
	}

	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class ReplyRequest {
		private String content;
		private Long messageId;
		private String nickname;
		private Long retentionTime;
		private String replyAvailable;

		@Builder
		public ReplyRequest(String content, Long messageId, String nickname, Long retentionTime,
			String replyAvailable) {
			this.content = content;
			this.messageId = messageId;
			this.nickname = nickname;
			this.retentionTime = retentionTime;
			this.replyAvailable = replyAvailable;
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
		private String receiverNickname;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime createTime;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime expirationTime;
		private String replyAvailable;

		@Builder
		public Response(Long messageId, String content, String state, Long receiver, String nickname,
			String receiverNickname, LocalDateTime createTime, LocalDateTime expirationTime,
			String replyAvailable) {
			this.messageId = messageId;
			this.content = content;
			this.state = state;
			this.receiver = receiver;
			this.nickname = nickname;
			this.receiverNickname = receiverNickname;
			this.createTime = createTime;
			this.expirationTime = expirationTime;
			this.replyAvailable = replyAvailable;
		}

		public static Response from(MessageDto messageDto) {
			return Response.builder()
				.messageId(messageDto.getMessageId())
				.content(messageDto.getContent())
				.state(messageDto.getState().getMessage())
				.receiver(messageDto.getReceiver())
				.nickname(messageDto.getNickname())
				.receiverNickname(messageDto.getReceiverNickname())
				.createTime(messageDto.getCreateTime())
				.expirationTime(messageDto.getExpirationTime())
				.replyAvailable(messageDto.getReplyAvailable())
				.build();
		}

	}

	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class CountResponse {
		private Long receivedMessagesCount;
		private Long sentMessagesCount;
		private Long expiredMessagesCount;

		@Builder
		public CountResponse(Long receivedMessagesCount, Long sentMessagesCount, Long expiredMessagesCount) {
			this.receivedMessagesCount = receivedMessagesCount;
			this.sentMessagesCount = sentMessagesCount;
			this.expiredMessagesCount = expiredMessagesCount;
		}

		public static CountResponse from(MessageCountDto messageCountDto) {
			return CountResponse.builder()
				.receivedMessagesCount(messageCountDto.getReceivedMessagesCount())
				.sentMessagesCount(messageCountDto.getSentMessagesCount())
				.expiredMessagesCount(messageCountDto.getExpiredMessagesCount())
				.build();
		}
	}
}
