package com.ashe.popping.domain.message.dto;

import lombok.Getter;

@Getter
public enum MessageState {
	EXPIRED(-1, "만료됨"),
	UNREAD(0, "안읽음"),
	READ(1, "읽음");

	MessageState(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private final int code;
	private final String message;
}
