package com.ashe.popping.global.redis.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.ashe.popping.global.redis.dto.RefreshToken;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
	private final RedisTemplate redisTemplate;

	public void save(RefreshToken refreshToken) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(String.valueOf(refreshToken.getMemberId()), refreshToken.getRefreshToken());
		redisTemplate.expire(refreshToken.getRefreshToken(), 14, TimeUnit.DAYS); // 14일후에 만료
	}

}
