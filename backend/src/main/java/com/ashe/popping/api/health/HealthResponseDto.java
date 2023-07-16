package com.ashe.popping.api.health;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HealthResponseDto {
	private String health;
	private List<String> activeProfiles;
	private String localServerPort;
	private String serverPort;

	public static HealthResponseDto of(String health, List<String> activeProfiles, String localServerPort,
		String serverPort) {
		return HealthResponseDto.builder()
			.health(health)
			.activeProfiles(activeProfiles)
			.localServerPort(localServerPort)
			.serverPort(serverPort)
			.build();
	}
}
