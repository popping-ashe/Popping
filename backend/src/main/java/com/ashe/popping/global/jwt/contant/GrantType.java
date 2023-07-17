package com.ashe.popping.global.jwt.contant;

import lombok.Getter;

@Getter
public enum GrantType {
	BEARER("Bearer");

	private String type;

	GrantType(String type){
		this.type = type;
	}
}
