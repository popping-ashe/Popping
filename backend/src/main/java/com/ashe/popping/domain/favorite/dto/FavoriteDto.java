package com.ashe.popping.domain.favorite.dto;

import com.ashe.popping.api.favorite.dto.FavoriteApiDto;
import com.ashe.popping.domain.favorite.entity.Favorite;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FavoriteDto {
	private Long memberId;
	private Long favoriteId;

	@Builder
	public FavoriteDto(Long memberId, Long favoriteId) {
		this.memberId = memberId;
		this.favoriteId = favoriteId;
	}

	static public FavoriteDto from(Favorite favorite) {
		return FavoriteDto.builder()
			.memberId(favorite.getMemberId())
			.favoriteId(favorite.getFavoriteId())
			.build();
	}

	static public FavoriteDto from(FavoriteApiDto.Request favorite, Long memberId) {
		return FavoriteDto.builder()
			.memberId(memberId)
			.favoriteId(favorite.getFavoriteId())
			.build();
	}
}
