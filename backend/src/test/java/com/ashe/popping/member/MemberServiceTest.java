package com.ashe.popping.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ashe.popping.domain.member.repository.MemberRepository;
import com.ashe.popping.domain.member.service.MemberService;

@SpringBootTest
public class MemberServiceTest {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;
	
}
