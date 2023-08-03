package com.ashe.popping.global.util;

import org.springframework.util.StringUtils;

import com.ashe.popping.global.error.ErrorCode;
import com.ashe.popping.global.error.exception.AuthenticationException;
import com.ashe.popping.global.jwt.contant.GrantType;

public class AuthorizationHeaderUtils {
	public static void validateAuthorization(String authorizationHeader) {
		// authorizationHeader 필수 체크
		if (!StringUtils.hasText(authorizationHeader)) {
			throw new AuthenticationException(ErrorCode.NOT_EXIST_AUTHORIZATION);
		}
		String[] authorizations = authorizationHeader.split(" ");
		if (authorizationTypeCheck(authorizations)) {
			throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
		}
	}

	private static boolean authorizationTypeCheck(String[] authorizations) {
		return authorizations.length < 2 || (!GrantType.BEARER.getType().equals(authorizations[0]));
	}

}
