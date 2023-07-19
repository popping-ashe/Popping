package com.ashe.popping.domain.message.dto;

import lombok.Getter;

@Getter
public enum MessageState {
	EXPIRED("만료됨"),
	UNREAD("안읽음"),
	READ("읽음");

	MessageState(String message) {
		this.message = message;
	}

	private final String message;
}
