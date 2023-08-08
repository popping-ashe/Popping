package com.ashe.popping.api.signout.dto;

import org.springframework.stereotype.Component;
import lombok.*;

@Component
public class KakaoDisconnectDto {
	@Builder
	@Getter
	@ToString
	public static class Request {
		private final String target_id_type="user_id";

		private String target_id;

		public static Request of(String targetId) {
			return builder()
				.target_id(targetId)
				.build();
		}

	}

	@Builder
	@Getter
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private String id;
	}
}