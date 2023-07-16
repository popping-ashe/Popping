package com.ashe.popping.api.health;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {
	private final Environment environment;

	@GetMapping()
	public ResponseEntity<HealthResponseDto> checkHealth() {
		HealthResponseDto healthResponseDto = HealthResponseDto.of("ok", Arrays.asList(environment.getActiveProfiles()),
			environment.getProperty("local.server.port"), environment.getProperty("server.port"));

		return ResponseEntity.ok(healthResponseDto);
	}

}
