package com.ashe.popping.global.error.exception;

import com.ashe.popping.global.error.ErrorCode;

public class AuthenticationException extends BusinessException{
	public AuthenticationException(ErrorCode errorCode){
		super(errorCode);
	}
}
