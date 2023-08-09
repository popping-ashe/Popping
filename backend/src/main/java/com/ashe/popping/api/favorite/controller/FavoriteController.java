package com.ashe.popping.api.favorite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.favorite.dto.FavoriteApiDto;
import com.ashe.popping.domain.favorite.dto.FavoriteDto;
import com.ashe.popping.domain.favorite.dto.FavoriteListDto;
import com.ashe.popping.domain.favorite.service.FavoriteService;
import com.ashe.popping.global.resolver.memberinfo.MemberInfo;
import com.ashe.popping.global.resolver.memberinfo.MemberInfoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {
	private final FavoriteService favoriteService;

	@PostMapping
	public ResponseEntity<FavoriteApiDto.Response> saveFavorite(@MemberInfo MemberInfoDto memberInfoDto,
		@RequestBody FavoriteApiDto.Request request) {
		Long memberId = memberInfoDto.getMemberId();
		FavoriteDto favoriteDto = favoriteService.saveFavorite(FavoriteDto.from(request, memberId));
		return ResponseEntity.ok(FavoriteApiDto.Response.from(favoriteDto));
	}
	@GetMapping
	public ResponseEntity<FavoriteApiDto.ListResponse> getFavorites(@MemberInfo MemberInfoDto memberInfoDto) {
		Long memberId = memberInfoDto.getMemberId();
		FavoriteListDto favoriteListDto = favoriteService.loadFavorites(memberId);
		return ResponseEntity.ok(FavoriteApiDto.ListResponse.from(favoriteListDto));
	}

	@DeleteMapping
	public ResponseEntity<?> unFavorite(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody FavoriteApiDto.Request request){
		Long memberId = memberInfoDto.getMemberId();
		favoriteService.removeFavorite(FavoriteDto.from(request, memberId));
		return ResponseEntity.ok(null);
	}

}
