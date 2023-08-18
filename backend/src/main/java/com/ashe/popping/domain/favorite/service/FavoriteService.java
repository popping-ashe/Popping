package com.ashe.popping.domain.favorite.service;

import java.util.List;

import com.ashe.popping.domain.favorite.dto.FavoriteDto;

public interface FavoriteService {

	// 즐겨찾기 등록
	FavoriteDto saveFavorite(FavoriteDto favoriteDto);

	// 즐겨찾기 조회
	List<Long> loadFavorites(Long memberId);

	// 즐겨찾기 삭제
	void removeFavorite(FavoriteDto favoriteDto);

	void removeWithdrawalMember(Long memberId);
}
