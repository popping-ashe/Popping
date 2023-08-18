package com.ashe.popping.global.resolver.memberinfo;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ashe.popping.global.jwt.service.TokenManager;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberInfoArgumentResolver implements HandlerMethodArgumentResolver {

	private final TokenManager tokenManager;

	/*
	 * 주어진 메소드의 파리미터가 이 Argument Resolver에서 지원하는 타입인지 검사
	 * 맞다면 true, 아니라면 false를 반환
	 * */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasMemberInfoAnnotaion = parameter.hasParameterAnnotation(MemberInfo.class);
		boolean hasMemberInfoDto = MemberInfoDto.class.isAssignableFrom(parameter.getParameterType());
		return hasMemberInfoAnnotaion && hasMemberInfoDto;
	}

	/*
	 * 이 메소드의 반환값이 대상이 되는 메소드의 파라미터에 바인딩된다.
	 *
	 * @MemberInfo 어노테이션이 호출되었을 때 실행되는 부분
	 * */
	@Override
	public MemberInfoDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// 토큰에 있던 정보를 MemberInfoDto로 반들어 반환
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		String authorizationHeader = request.getHeader("Authorization");
		String token = authorizationHeader.split(" ")[1];
		Claims tokenClaims = tokenManager.getTokenClaims(token);
		Long memberId = Long.valueOf((Integer)tokenClaims.get("memberId"));
		String role = (String)tokenClaims.get("role");
		return MemberInfoDto.of(memberId, role);
	}
}
