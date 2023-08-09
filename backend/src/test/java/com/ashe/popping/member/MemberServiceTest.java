package com.ashe.popping.member;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.repository.MemberRepository;
import com.ashe.popping.domain.member.service.MemberService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberServiceTest {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	@Container
	static MariaDBContainer mariaDBContainer = new MariaDBContainer().withDatabaseName("popping");

	@Test
	@DisplayName("create member")
	@Order(1)
	void createMember() {
		MemberDto member1 = MemberDto.builder()
			.memberId(1L)
			.shareId(1234567890L)
			.socialLoginId("red")
			.memberType(MemberType.KAKAO)
			.nickname("irene")
			.createdTime(LocalDateTime.now())
			.lastVisitedTime(LocalDateTime.now())
			.role(Role.USER)
			.bio("russian roulette")
			.build();
		MemberDto member2 = MemberDto.builder()
			.memberId(2L)
			.shareId(1234567891L)
			.memberType(MemberType.GOOGLE)
			.socialLoginId("velvet")
			.nickname("wendy")
			.createdTime(LocalDateTime.now())
			.lastVisitedTime(LocalDateTime.now())
			.role(Role.ADMIN)
			.bio("feel my rhythm")
			.build();
		MemberDto createdMember1 = memberService.createMember(member1);
		MemberDto createdMember2 = memberService.createMember(member2);

		Assertions.assertEquals(member1.getMemberId(), createdMember1.getMemberId());
		Assertions.assertEquals(member1.getBio(), createdMember1.getBio());
		Assertions.assertEquals(member1.getNickname(), createdMember1.getNickname());
		Assertions.assertEquals(member1.getSocialLoginId(), createdMember1.getSocialLoginId());
		Assertions.assertEquals(member1.getMemberType(), createdMember1.getMemberType());
		Assertions.assertEquals(member1.getShareId(), createdMember1.getShareId());
		Assertions.assertEquals(member1.getLastVisitedTime(), createdMember1.getCreatedTime());
		Assertions.assertEquals(member1.getCreatedTime(), createdMember1.getCreatedTime());
		Assertions.assertEquals(member1.getRole(), createdMember1.getRole());

		Assertions.assertEquals(member2.getMemberId(), createdMember2.getMemberId());
		Assertions.assertEquals(member2.getBio(), createdMember2.getBio());
		Assertions.assertEquals(member2.getNickname(), createdMember2.getNickname());
		Assertions.assertEquals(member2.getSocialLoginId(), createdMember2.getSocialLoginId());
		Assertions.assertEquals(member2.getMemberType(), createdMember2.getMemberType());
		Assertions.assertEquals(member2.getShareId(), createdMember2.getShareId());
		Assertions.assertEquals(member2.getLastVisitedTime(), createdMember2.getCreatedTime());
		Assertions.assertEquals(member2.getCreatedTime(), createdMember2.getCreatedTime());
		Assertions.assertEquals(member2.getRole(), createdMember2.getRole());
	}
}
