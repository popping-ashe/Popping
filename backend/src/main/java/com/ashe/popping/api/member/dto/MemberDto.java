package com.ashe.popping.api.member.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.ashe.popping.domain.member.constant.Role;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
public class MemberDto {
	private Long memberId;
	private String nickname;
	private LocalDateTime lastVisitedTime;
	private String uuid;
	private LocalDateTime createdTime;
	private Role role;

	@Builder
	@Data
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class Response {
		private Long memberId;
		private String nickname;
	}
}
