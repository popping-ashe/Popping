package com.ashe.popping.api.share.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShareApiResultDto<T> {
	private String nickname;
	private String bio;
	private T data;

	@Builder
	public ShareApiResultDto(String nickname, String bio, T data) {
		this.nickname = nickname;
		this.bio = bio;
		this.data = data;
	}

	public static ShareApiResultDto<List<ShareApiDto.MessageResponse>> of(String nickname, String bio,
		List<ShareApiDto.MessageResponse> data) {
		return ShareApiResultDto.<List<ShareApiDto.MessageResponse>>builder()
			.nickname(nickname)
			.bio(bio)
			.data(data)
			.build();
	}
}
