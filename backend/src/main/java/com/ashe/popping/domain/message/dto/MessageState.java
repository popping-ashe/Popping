package com.ashe.popping.domain.message.dto;

import lombok.Getter;

@Getter
public enum MessageState {
	EXPIRED(-1),
	UNREAD(0),
	READ(1);

	MessageState(int code) {
		this.code = code;
	}

	private final int code;
}
