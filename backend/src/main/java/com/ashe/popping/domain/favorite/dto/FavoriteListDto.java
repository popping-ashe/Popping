package com.ashe.popping.domain.favorite.dto;

import java.util.List;

import com.ashe.popping.domain.favorite.entity.FavoriteMapping;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FavoriteListDto {
	private Long memberId;
	private List<Long> favorites;

	@Builder
	public FavoriteListDto(Long memberId, List<Long> favorites) {
		this.memberId = memberId;
		this.favorites = favorites;
	}

	static public FavoriteListDto of(Long memberId, List<FavoriteMapping> favoriteMappings) {
		return FavoriteListDto.builder()
			.memberId(memberId)
			.favorites(favoriteMappings.stream()
				.map(FavoriteMapping::getFavoriteId)
				.toList())
			.build();
	}
}
