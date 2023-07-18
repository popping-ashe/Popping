package com.ashe.popping.domain.member.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.ashe.popping.api.member.dto.MemberDto;
import com.ashe.popping.domain.member.constant.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(nullable = false, length = 50)
	private String nickname;

	private LocalDateTime lastVisitedTime;

	@Column(nullable = false, unique = true)
	private String kakaoId;

	@CreatedDate
	private LocalDateTime createdTime;

	@Enumerated(EnumType.STRING)
	private Role role;

	public static Member from(MemberDto memberDto) {
		return Member.builder()
			.memberId(memberDto.getMemberId())
			.nickname(memberDto.getNickname())
			.lastVisitedTime(memberDto.getLastVisitedTime())
			.kakaoId(memberDto.getKakaoId())
			.createdTime(memberDto.getCreatedTime())
			.role(memberDto.getRole())
			.build();
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}
}
