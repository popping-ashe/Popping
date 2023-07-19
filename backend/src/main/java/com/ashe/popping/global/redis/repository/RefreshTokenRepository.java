package com.ashe.popping.global.redis.repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.swing.text.html.Option;

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

	public Optional<RefreshToken> findByMemberId(String refreshToken){
		ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
		Long memberId = valueOperations.get(refreshToken);

		if(Objects.isNull(memberId)){
			return Optional.empty();
		}
		return Optional.of(new RefreshToken(refreshToken, memberId));
	}
}
