package com.ashe.popping.api.favorite.dto;

import java.util.List;

import com.ashe.popping.domain.favorite.dto.FavoriteDto;
import com.ashe.popping.domain.favorite.dto.FavoriteListDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FavoriteApiDto {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Request {
		private Long favoriteId;

		@Builder
		public Request(Long favoriteId) {
			this.favoriteId = favoriteId;
		}
	}

	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Response {
		private Long memberId;
		private Long favoriteId;

		@Builder
		public Response(Long memberId, Long favoriteId) {
			this.memberId = memberId;
			this.favoriteId = favoriteId;
		}

		public static FavoriteApiDto.Response from(FavoriteDto favoriteDto) {
			return Response.builder()
				.memberId(favoriteDto.getMemberId())
				.favoriteId(favoriteDto.getFavoriteId())
				.build();
		}
	}

	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class ListResponse {
		private Long memberId;
		private List<Long> favorites;

		@Builder
		public ListResponse(Long memberId, List<Long> favorites) {
			this.memberId = memberId;
			this.favorites = favorites;
		}

		public static ListResponse from(FavoriteListDto favoriteListDto){
			return ListResponse.builder()
				.memberId(favoriteListDto.getMemberId())
				.favorites(favoriteListDto.getFavorites())
				.build();
		}
	}
}
