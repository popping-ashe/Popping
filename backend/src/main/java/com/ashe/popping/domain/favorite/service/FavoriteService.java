package com.ashe.popping.domain.favorite.service;

import com.ashe.popping.domain.favorite.dto.FavoriteDto;
import com.ashe.popping.domain.favorite.dto.FavoriteListDto;

public interface FavoriteService {

	// 즐겨찾기 등록
	FavoriteDto saveFavorite(FavoriteDto favoriteDto);

	// 즐겨찾기 조회
	FavoriteListDto loadFavorites(Long memberId);

	// 즐겨찾기 삭제
	void removeFavorite(FavoriteDto favoriteDto);

}
