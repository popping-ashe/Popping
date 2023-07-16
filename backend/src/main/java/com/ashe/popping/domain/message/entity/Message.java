package com.ashe.popping.domain.message.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long messageId;

	@Column(nullable = false)
	private Integer state;

	@Column(nullable = false)
	private String content;

	@CreatedDate
	private LocalDateTime createTime;

	@Column(nullable = false)
	private LocalDateTime expirationTime;

	private Long sender;

	@Column(nullable = false)
	private Long receiver;

	@Column(nullable = false)
	private String nickname;
}
