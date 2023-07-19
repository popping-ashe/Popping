package com.ashe.popping.member;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.repository.MemberRepository;
import com.ashe.popping.domain.member.service.MemberService;

@SpringBootTest
public class MemberServiceTest {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	void 테스트_회원_넣기() {
		MemberDto memberDto = MemberDto.builder()
			.memberId(1L)
			.nickname("관리자")
			.lastVisitedTime(LocalDateTime.now())
			.kakaoId("admin")
			.createdTime(LocalDateTime.now())
			.role(Role.ADMIN)
			.build();
		memberService.createMember(memberDto);

		for (int i = 2; i <= 6; i++) {
			MemberDto member = MemberDto.builder()
				.memberId((long)i)
				.nickname(i + "번째 회원")
				.lastVisitedTime(LocalDateTime.now())
				.kakaoId("kakaoid" + i + i)
				.createdTime(LocalDateTime.now())
				.role(Role.USER)
				.build();
			memberService.createMember(member);
		}
	}

	@Test
	@DisplayName("회원 정보를 가져온다.")
	void 회원_정보_조회() {
		테스트_회원_넣기();
		MemberDto memberDto = memberService.getMemberByMemberId(2L);
		Assertions.assertThat(memberDto.getNickname()).isEqualTo("2번째 회원");
		Assertions.assertThat(memberDto.getRole()).isEqualTo(Role.USER);
		Assertions.assertThat(memberDto.getKakaoId()).isEqualTo("kakaoid22");
	}

	@Test
	@DisplayName("회원 정보를 수정한다.")
	void 회원_정보_수정() {
		테스트_회원_넣기();
		MemberDto memberDto = MemberDto.builder()
			.memberId(3L)
			.nickname("3번째회원닉네임바뀜")
			.build();

		memberService.updateMember(memberDto);
		MemberDto result = memberService.getMemberByMemberId(3L);
		Assertions.assertThat(memberDto.getNickname()).isEqualTo("3번째회원닉네임바뀜");
	}
}
