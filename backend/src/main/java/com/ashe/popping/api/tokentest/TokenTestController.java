package com.ashe.popping.api.tokentest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.global.jwt.dto.JwtTokenDto;
import com.ashe.popping.global.jwt.service.TokenManager;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/token-test")
@RequiredArgsConstructor
public class TokenTestController {

	private final TokenManager tokenManager;

	@GetMapping("/create")
	public JwtTokenDto createJwtTokenDto(){
		return tokenManager.createJwtTokenDto(1L, Role.ADMIN);
	}

	@GetMapping("/valid")
	public String validateJwtToken(@RequestParam String token){
		tokenManager.validateToken(token);
		Claims tokenClaims = tokenManager.getTokenClaims(token);
		long memberId = Long.valueOf((Integer) tokenClaims.get("memberId"));
		String role = (String) tokenClaims.get("role");
		log.info("memberId : {}", memberId);
		log.info("role : {}", role);
		return "success";
	}
}
