package com.ashe.popping.api.favorite.dto;

import java.util.List;

import com.ashe.popping.domain.member.dto.MemberDto;
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

		public static FavoriteApiDto.Response of(Long memberId, Long favoriteId) {
			return Response.builder()
				.memberId(memberId)
				.favoriteId(favoriteId)
				.build();
		}
	}

	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class FavoriteMember {
		private String nickname;
		private String bio;
		private Long shareId;

		@Builder
		public FavoriteMember(String nickname, String bio, Long shareId) {
			this.nickname = nickname;
			this.bio = bio;
			this.shareId = shareId;
		}

		public static FavoriteMember from(MemberDto memberDto) {
			return FavoriteMember.builder()
				.nickname(memberDto.getNickname())
				.bio(memberDto.getBio())
				.shareId(memberDto.getShareId())
				.build();
		}
	}

	@Getter
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class ListResponse {
		private Long memberId;
		private List<FavoriteMember> favorites;

		@Builder
		public ListResponse(Long memberId, List<FavoriteMember> favorites) {
			this.memberId = memberId;
			this.favorites = favorites;
		}

		public static ListResponse of(Long memberId, List<FavoriteMember> favorites) {
			return ListResponse.builder()
				.memberId(memberId)
				.favorites(favorites)
				.build();
		}
	}
}
