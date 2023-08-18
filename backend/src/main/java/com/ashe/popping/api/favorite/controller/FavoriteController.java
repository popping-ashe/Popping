package com.ashe.popping.api.favorite.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.favorite.dto.FavoriteApiDto;
import com.ashe.popping.domain.favorite.dto.FavoriteDto;
import com.ashe.popping.domain.favorite.service.FavoriteService;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.global.resolver.memberinfo.MemberInfo;
import com.ashe.popping.global.resolver.memberinfo.MemberInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {
	private final FavoriteService favoriteService;
	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<FavoriteApiDto.Response> saveFavorite(@MemberInfo MemberInfoDto memberInfoDto,
		@RequestBody FavoriteApiDto.Request request) {
		Long memberId = memberInfoDto.getMemberId();
		Long favoriteId = memberService.getMemberByShareId(request.getFavoriteId()).getMemberId();
		FavoriteDto favoriteDto = favoriteService.saveFavorite(FavoriteDto.of(memberId, favoriteId));
		return ResponseEntity.ok(FavoriteApiDto.Response.of(favoriteDto.getMemberId(), request.getFavoriteId()));
	}

	@GetMapping
	public ResponseEntity<FavoriteApiDto.ListResponse> getFavorites(@MemberInfo MemberInfoDto memberInfoDto) {
		Long memberId = memberInfoDto.getMemberId();
		List<Long> favorites = favoriteService.loadFavorites(memberId);
		List<FavoriteApiDto.FavoriteMember> favoriteMembers = favorites.stream()
			.map(l -> {
				try {
					MemberDto m = memberService.getMemberByMemberId(l);
					return FavoriteApiDto.FavoriteMember.from(m);
				} catch (Exception e) {
					log.info("탈퇴한 회원을 즐겨찾기 하고 있습니다.", e);
					return null;
				}
			}).filter(Objects::nonNull).toList();
		return ResponseEntity.ok(FavoriteApiDto.ListResponse.of(memberId, favoriteMembers));
	}

	@DeleteMapping
	public ResponseEntity<?> unFavorite(@MemberInfo MemberInfoDto memberInfoDto,
		@RequestBody FavoriteApiDto.Request request) {
		Long memberId = memberInfoDto.getMemberId();
		Long favoriteId = memberService.getMemberByShareId(request.getFavoriteId()).getMemberId();
		favoriteService.removeFavorite(FavoriteDto.of(memberId, favoriteId));
		return ResponseEntity.ok(null);
	}

}
