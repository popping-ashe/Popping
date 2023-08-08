package com.ashe.popping.api.login.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ashe.popping.api.login.client.KakaoTokenClient;
import com.ashe.popping.api.login.dto.KakaoTokenDto;
import com.ashe.popping.api.message.dto.MessageApiDto;
import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.entity.Member;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.service.MessageService;
import com.ashe.popping.domain.terms.dto.TermsDto;
import com.ashe.popping.domain.terms.service.TermsService;
import com.ashe.popping.domain.termsagreement.dto.TermsAgreementDto;
import com.ashe.popping.domain.termsagreement.dto.TermsAgreementState;
import com.ashe.popping.domain.termsagreement.service.TermsAgreementService;
import com.ashe.popping.external.oauth.kakao.dto.KakaoMemberInfoResponseDto;
import com.ashe.popping.external.oauth.kakao.service.KakaoLoginApiService;
import com.ashe.popping.external.oauth.kakao.service.KakaoLoginApiServiceImpl;
import com.ashe.popping.external.oauth.model.OAuthAttributes;
import com.ashe.popping.external.oauth.service.SocialLoginApiService;
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
	private final SocialLoginApiService socialLoginApiService;
	private final TokenManager tokenManager;
	private final MemberService memberService;
	private final MessageService messageService;
	private final TermsService termsService;
	private final TermsAgreementService termsAgreementService;

	@Value("${kakao.client.id}")
	private String clientId;

	@Value("${kakao.client.secret}")
	private String clientSecret;

	@Override
	public JwtTokenDto kakaoLogin(String code) {
		String contentType = "application/x-www-form-urlencoded;charset=utf-8";
		KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.of(clientId, clientSecret, code);
		KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
		OAuthAttributes memberInfo = socialLoginApiService.getMemberInfo(
			GrantType.BEARER.getType() + " " + kakaoToken.getAccessToken());
		JwtTokenDto jwtTokenDto;
		Optional<Member> optionalMember = memberService.getMemberByKakaoId(memberInfo.getId());

		// 1. 신규 회원
		if (optionalMember.isEmpty()) {
			// 공유 url에 필요한 난수 생성 및 회원 생성
			MemberDto oauthMember = makeShareId(memberInfo);
			// 웰컴 메세지 보내기
			MessageDto wellComeMessage = makeWelComeMessage();
			messageService.saveMessage(wellComeMessage, oauthMember);

			List<TermsDto> terms = termsService.getAllTerms();

			terms.forEach(t -> termsAgreementService.createTermsAgreement(
				TermsAgreementDto.of(oauthMember.getMemberId(), t.getTermsId(),
					TermsAgreementState.PENDING)));

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

	MemberDto makeShareId(OAuthAttributes memberInfo) {
		SecureRandom random = new SecureRandom();
		MemberDto oauthMember;

		while (true) {
			Long shareId = random.nextLong(1000000000L, 10000000000L);
			oauthMember = memberInfo.toMemberDto(MemberType.KAKAO, Role.USER, shareId);
			try {
				oauthMember = memberService.createMember(oauthMember);
				break;
			} catch (Exception e) {
				log.info("중복 공유 아이디 생성", e);
			}
		}
		return oauthMember;
	}

	MessageDto makeWelComeMessage() {
		String content = "\uD83D\uDC99환영합니다\uD83D\uDC99\n"
			+ "제한 시간이 지나면 버블은 사라지니 얼른 읽어주세요⏰\n"
			+ "친구의 링크를 공유받아 버블을 남겨보세요\uD83D\uDD17";
		String nickname = "popping";
		Long retentionTime = 24L;
		MessageApiDto.Request request = MessageApiDto.Request.from(content, nickname, retentionTime);
		return MessageDto.from(request);
	}
}
