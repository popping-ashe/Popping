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

	public void save(final RefreshToken refreshToken){
		ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(refreshToken.getRefreshToken(), refreshToken.getMemberId());
		redisTemplate.expire(refreshToken.getRefreshToken(), 14, TimeUnit.DAYS); // 14일후에 만료
	}
}
