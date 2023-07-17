package com.ashe.popping.domain.message.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {

	private Integer state;

	private String content;

	private LocalDateTime expirationTime;

	private Long sender;

	private Long receiver;

	private String nickname;
}
