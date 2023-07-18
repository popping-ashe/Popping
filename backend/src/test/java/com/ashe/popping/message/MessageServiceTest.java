package com.ashe.popping.message;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.repository.MessageRepository;
import com.ashe.popping.domain.message.service.MessageService;

@SpringBootTest
public class MessageServiceTest {

	@Autowired
	MessageService messageService;

	@Autowired
	MessageRepository messageRepository;

	@Test
	@DisplayName("메세지가 전송된다.")
	void 메세지_전송() {
		MessageDto messageDto = MessageDto.builder()
			.state(-1)
			.content("하이하이~ 정훈이에염~")
			.sender(null)
			.receiver(1L)
			.nickname("정훈이")
			.expirationTime(LocalDateTime.now())
			.build();
		MessageDto message = messageService.saveMessage(messageDto);
		Assertions.assertThat(message.getContent()).isEqualTo("하이하이~ 정훈이에염~");
		Assertions.assertThat(message.getNickname()).isEqualTo("정훈이");
		Assertions.assertThat(message.getReceiver()).isEqualTo(1L);
		Assertions.assertThat(message.getSender()).isNull();
		System.out.println(message.getContent());
	}

	@Test
	@DisplayName("내가 받은 메시지를 불러온다.")
	void 받은_메시지() {
		테스트_메시지_넣기();
		List<MessageDto> messages = messageService.loadReceiveMessage(1L);
		Assertions.assertThat(messages.size()).isEqualTo(6);
		for (int i = 0; i < 6; i++) {
			Assertions.assertThat(messages.get(i).getReceiver()).isEqualTo(1L);
		}
	}

	@Test
	@DisplayName("내가 보낸 메시지를 불러온다.")
	void 보낸_메시지() {
		테스트_메시지_넣기();
		List<MessageDto> messages = messageService.loadSenderMessage(1L);
		Assertions.assertThat(messages.size()).isEqualTo(6);
		for (int i = 0; i < 6; i++) {
			Assertions.assertThat(messages.get(i).getSender()).isEqualTo(1L);
		}
	}

	void 테스트_메시지_넣기() {
		for (int i = 1; i <= 5; i++) {
			MessageDto messageDto = MessageDto.builder()
				.state(-1)
				.content("테스트 코드" + i)
				.sender((long)i)
				.receiver(1L)
				.nickname("테스트" + i)
				.expirationTime(LocalDateTime.now())
				.build();
			MessageDto message = messageService.saveMessage(messageDto);
		}
		for (int i = 6; i <= 10; i++) {
			MessageDto messageDto = MessageDto.builder()
				.state(-1)
				.content("테스트 코드" + i)
				.sender(1L)
				.receiver((long)(i - 5))
				.nickname("테스트" + i)
				.expirationTime(LocalDateTime.now())
				.build();
			MessageDto message = messageService.saveMessage(messageDto);
		}
	}
}
