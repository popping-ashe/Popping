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
}
