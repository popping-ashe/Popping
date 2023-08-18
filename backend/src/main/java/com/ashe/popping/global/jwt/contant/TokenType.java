package com.ashe.popping.global.jwt.contant;

public enum TokenType {
	ACCESS, REFRESH;

	public static boolean isAccessToken(String tokenType) {
		return TokenType.ACCESS.name().equals(tokenType);
	}

	public static boolean isRefreshToken(String tokenType) {
		return TokenType.REFRESH.name().equals(tokenType);
	}
}
