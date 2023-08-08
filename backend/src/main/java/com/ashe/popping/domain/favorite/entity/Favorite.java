package com.ashe.popping.domain.favorite.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ashe.popping.domain.favorite.dto.FavoriteDto;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(FavoritePK.class)
public class Favorite {
	@Id
	private Long memberId;

	@Id
	private Long favoriteId;

	static public Favorite from(FavoriteDto favoriteDto) {
		return Favorite.builder()
			.memberId(favoriteDto.getMemberId())
			.favoriteId(favoriteDto.getFavoriteId())
			.build();
	}
}