package com.ashe.popping.api.login.service;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ashe.popping.api.login.client.KakaoTokenClient;
import com.ashe.popping.api.login.dto.KakaoTokenDto;
import com.ashe.popping.api.message.dto.MessageApiDto;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.entity.Member;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.service.MessageService;
import com.ashe.popping.external.oauth.kakao.dto.KakaoMemberInfoResponseDto;
import com.ashe.popping.external.oauth.kakao.service.KakaoLoginApiService;
import com.ashe.popping.global.jwt.contant.GrantType;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;
import com.ashe.popping.global.jwt.service.TokenManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginServiceImpl implements KakaoLoginService {
	private final KakaoTokenClient kakaoTokenClient;
	private final KakaoLoginApiService kakaoLoginApiService;
	private final TokenManager tokenManager;
	private final MemberService memberService;
	private final MessageService messageService;

	@Value("${kakao.client.id}")
	private String clientId;

	@Value("${kakao.client.secret}")
	private String clientSecret;

	@Override
	public JwtTokenDto kakaoLogin(String code) {
		String contentType = "application/x-www-form-urlencoded;charset=utf-8";
		KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.of(clientId, clientSecret, code);
		KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
		KakaoMemberInfoResponseDto memberInfo = kakaoLoginApiService.getMemberInfo(
			GrantType.BEARER.getType() + " " + kakaoToken.getAccessToken());
		JwtTokenDto jwtTokenDto;
		Optional<Member> optionalMember = memberService.getMemberByKakaoId(memberInfo.getKakaoId());

		// 1. 신규 회원
		if (optionalMember.isEmpty()) {
			// 공유 url에 필요한 난수 생성 및 회원 생성
			MemberDto oauthMember = makeShareId(memberInfo);
			// 웰컴 메세지 보내기
			MessageDto wellComeMessage = makeWellComeMessage();
			MessageDto messageDto = messageService.saveMessage(wellComeMessage, oauthMember);
			ResponseEntity.ok(MessageApiDto.Response.from(messageDto));


			// 토큰 생성
			jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
		}
		// 2. 가입된 회원
		else {
			Member oauthMember = optionalMember.get();
			// 토큰 생성
			jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
		}

		return jwtTokenDto;


	}
	MemberDto makeShareId(KakaoMemberInfoResponseDto memberInfo){
		SecureRandom random = new SecureRandom();
		MemberDto oauthMember;

		while(true){
			Long shareId = random.nextLong(1000000000L, 10000000000L);
			oauthMember = memberInfo.toMemberDto(Role.USER, shareId);
			try{
				oauthMember = memberService.createMember(oauthMember);
				break;
			}catch(Exception e){
				log.info("중복 공유 아이디 생성", e);
			}
		}
		return oauthMember;
	}

	MessageDto makeWellComeMessage(){
		String content = "환영합니다!\nShare 버튼을 눌러 내 페이지를 공유하세요!\n제한 시간이 지나면 버블은 사라집니다 얼른 읽어주세요\n버블 만들기를 눌러 다른 사람 페이지에 버블을 남겨보세요!";
		String nickname = "poppping 개발자";
		Long retentionTime = 24L;
		MessageApiDto.Request request = MessageApiDto.Request.from(content, nickname, retentionTime);
		return MessageDto.from(request);
	}
}
