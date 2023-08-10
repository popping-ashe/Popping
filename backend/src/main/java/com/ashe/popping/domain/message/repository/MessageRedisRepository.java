package com.ashe.popping.domain.message.repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.ashe.popping.domain.message.dto.MessageRedisDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MessageRedisRepository {
	private final RedisTemplate redisTemplate;

	public void save(MessageRedisDto messageRedisDto) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		String key = new StringBuilder()
			.append("messageId-")
			.append(messageRedisDto.getMessageId())
			.toString();
		valueOperations.set(key, "null");
		Long timeToLive = Duration.between(LocalDateTime.now(), messageRedisDto.getExpirationTime()).toSeconds();
		redisTemplate.expire(key, timeToLive, TimeUnit.SECONDS);
	}
}
