package com.ashe.popping.domain.member.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.ashe.popping.domain.member.constant.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable=false, length=50)
	private String nickname;

	private LocalDateTime lastVisitedTime;

	@Column(nullable = false, unique = true)
	private String uuid;

	@CreatedDate
	private LocalDateTime regTime;

	@Enumerated(EnumType.STRING)
	private Role role;
}
