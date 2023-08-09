package com.ashe.popping.external.oauth.google.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleMemberInfoResponseDto {
	private String id;

	@JsonProperty("name")
	private String nickname;

}
