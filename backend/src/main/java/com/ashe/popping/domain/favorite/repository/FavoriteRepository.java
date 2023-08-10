package com.ashe.popping.domain.favorite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashe.popping.domain.favorite.entity.Favorite;
import com.ashe.popping.domain.favorite.entity.FavoriteMapping;
import com.ashe.popping.domain.favorite.entity.FavoritePK;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoritePK> {
	List<FavoriteMapping> findByMemberId(Long memberId);

	int deleteByMemberId(Long memberId);

	int deleteByFavoriteId(Long favoriteId);
}
