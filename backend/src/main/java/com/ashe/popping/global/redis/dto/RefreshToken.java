package com.ashe.popping.global.redis.dto;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshToken {

	@Id
	private String id;

	private String refreshToken;

	@Indexed
	private String accessToken;
}
