package com.ashe.popping.api.profile;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
	private Environment env;

	@GetMapping
	public String getProfile() {
		return Arrays.stream(env.getActiveProfiles())
			.findFirst()
			.orElse("");
	}
}
