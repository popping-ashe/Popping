package com.ashe.popping.api.login.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ashe.popping.api.login.dto.OAuthTokenDto;
import com.ashe.popping.api.login.google.client.GoogleTokenClient;
import com.ashe.popping.api.login.kakao.client.KakaoTokenClient;
import com.ashe.popping.api.message.dto.MessageApiDto;
import com.ashe.popping.domain.member.constant.MemberType;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.service.MessageService;
import com.ashe.popping.domain.terms.dto.TermsDto;
import com.ashe.popping.domain.terms.service.TermsService;
import com.ashe.popping.domain.termsagreement.dto.TermsAgreementDto;
import com.ashe.popping.domain.termsagreement.dto.TermsAgreementState;
import com.ashe.popping.domain.termsagreement.service.TermsAgreementService;
import com.ashe.popping.external.oauth.model.OAuthAttributes;
import com.ashe.popping.external.oauth.service.SocialLoginApiService;
import com.ashe.popping.external.oauth.service.SocialLoginApiServiceFactory;
import com.ashe.popping.global.jwt.contant.GrantType;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;
import com.ashe.popping.global.jwt.service.TokenManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginServiceImpl implements OAuthLoginService {
	private final GoogleTokenClient googleTokenClient;
	private final KakaoTokenClient kakaoTokenClient;
	private final TokenManager tokenManager;
	private final MemberService memberService;
	private final MessageService messageService;
	private final TermsService termsService;
	private final TermsAgreementService termsAgreementService;

	@Value("${google.client.id}")
	private String googleClientId;

	@Value("${google.client.secret}")
	private String googleClientSecret;

	@Value("${kakao.client.id}")
	private String kakaoClientId;

	@Value("${kakao.client.secret}")
	private String kakaoClientSecret;

	@Value("${redirect-uri}")
	private String redirectUri;

	@Override
	public JwtTokenDto socialLogin(String code, MemberType memberType) {
		String contentType = "application/x-www-form-urlencoded;charset=utf-8";

		String type = memberType.name().toLowerCase();
		StringBuilder redirect = new StringBuilder().append(redirectUri).append(type).append("/callback");

		OAuthTokenDto.Response oAuthToken = null;
		if (MemberType.KAKAO.equals(memberType)) {
			OAuthTokenDto.Request oAuthRequestDto = OAuthTokenDto.Request.of(kakaoClientId, kakaoClientSecret, code,
				redirect.toString());
			oAuthToken = kakaoTokenClient.requestKakaoToken(contentType, oAuthRequestDto);
		}
		if (MemberType.GOOGLE.equals(memberType)) {
			OAuthTokenDto.Request oAuthRequestDto = OAuthTokenDto.Request.of(googleClientId, googleClientSecret, code,
				redirect.toString());
			oAuthToken = googleTokenClient.requestGoogleToken(contentType, oAuthRequestDto);
		}

		SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(memberType);

		OAuthAttributes memberInfo = socialLoginApiService.getMemberInfo(
			GrantType.BEARER.getType() + " " + oAuthToken.getAccessToken());
		JwtTokenDto jwtTokenDto;
		Optional<MemberDto> optionalMember = memberService.getMemberBySocialLoginIdAndMemberType(memberInfo.getId(),
			memberInfo.getMemberType());

		// 1. 신규 회원
		if (optionalMember.isEmpty()) {
			// 공유 url에 필요한 난수 생성 및 회원 생성
			MemberDto oauthMember = makeShareId(memberInfo, memberType);
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
			MemberDto oauthMember = optionalMember.get();
			if (oauthMember.getWithdrawalDate() != null) // 틸퇴 이력이 있는 회원이라면
				memberService.updateWithdrawalDate(oauthMember.getMemberId(), false);
			// 토큰 생성
			jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
		}

		return jwtTokenDto;

	}

	MemberDto makeShareId(OAuthAttributes memberInfo, MemberType memberType) {
		SecureRandom random = new SecureRandom();
		MemberDto oauthMember;

		while (true) {
			Long shareId = random.nextLong(1000000000L, 10000000000L);
			oauthMember = memberInfo.toMemberDto(memberType, Role.USER, shareId);
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
		String replyAvailable = "N";
		MessageApiDto.Request request = MessageApiDto.Request.of(content, nickname, retentionTime, replyAvailable);
		return MessageDto.from(request);
	}
}
