package com.ashe.popping.api.share.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShareApiResultDto<T> {
	private String nickname;
	private T data;

	@Builder
	public ShareApiResultDto(String nickname, T data) {
		this.nickname = nickname;
		this.data = data;
	}

	public static ShareApiResultDto<List<ShareApiDto.MessageResponse>> of(String nickname,
		List<ShareApiDto.MessageResponse> data) {
		return ShareApiResultDto.<List<ShareApiDto.MessageResponse>>builder()
			.nickname(nickname)
			.data(data)
			.build();
	}
}
