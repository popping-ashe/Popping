package com.ashe.popping.domain.favorite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ashe.popping.domain.favorite.dto.FavoriteDto;
import com.ashe.popping.domain.favorite.entity.Favorite;
import com.ashe.popping.domain.favorite.entity.FavoriteMapping;
import com.ashe.popping.domain.favorite.repository.FavoriteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

	private final FavoriteRepository favoriteRepository;

	@Override
	public FavoriteDto saveFavorite(FavoriteDto favoriteDto) {
		Favorite favorite = favoriteRepository.save(Favorite.from(favoriteDto));
		return FavoriteDto.from(favorite);
	}

	@Override
	public List<Long> loadFavorites(Long memberId) {
		List<FavoriteMapping> favoriteMappings = favoriteRepository.findByMemberId(memberId);
		return favoriteMappings.stream()
			.map(FavoriteMapping::getFavoriteId)
			.toList();
	}

	@Override
	public void removeFavorite(FavoriteDto favoriteDto) {
		favoriteRepository.delete(Favorite.from(favoriteDto));
	}

	@Override
	public void removeWithdrawalMember(Long memberId) {
		favoriteRepository.deleteByMemberId(memberId);
		favoriteRepository.deleteByFavoriteId(memberId);
	}
}
