package com.ashe.popping.global.redis.dto;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RefreshToken {

	@Id
	private Long memberId;

	private String refreshToken;

	public static RefreshToken of(Long memberId, String refreshToken) {
		return new RefreshToken(memberId, refreshToken);
	}

}
