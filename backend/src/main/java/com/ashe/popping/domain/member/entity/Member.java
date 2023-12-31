package com.ashe.popping.domain.member.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.dto.MemberDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private MemberType memberType;

	@Column(nullable = false, length = 30)
	private String nickname;

	private LocalDateTime lastVisitedTime;

	@Column(nullable = false)
	private String socialLoginId;

	@Column(nullable = false, unique = true)
	private Long shareId;

	@CreatedDate
	private LocalDateTime createdTime;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(nullable = false, length = 30)
	private String bio;

	private LocalDateTime withdrawalDate;

	public static Member from(MemberDto memberDto) {
		return Member.builder()
			.memberId(memberDto.getMemberId())
			.memberType(memberDto.getMemberType())
			.nickname(memberDto.getNickname())
			.lastVisitedTime(memberDto.getLastVisitedTime())
			.socialLoginId(memberDto.getSocialLoginId())
			.shareId(memberDto.getShareId())
			.createdTime(memberDto.getCreatedTime())
			.role(memberDto.getRole())
			.bio(memberDto.getBio())
			.build();
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateLastVisitedTime(LocalDateTime lastVisitedTime) {
		this.lastVisitedTime = lastVisitedTime;
	}

	public void updateBio(String bio) {
		this.bio = bio;
	}

	public void updateWithdrawalDate(LocalDateTime withdrawalDate) {
		this.withdrawalDate = withdrawalDate;
	}
}
