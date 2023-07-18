package com.ashe.popping.message;

import java.time.LocalDateTime;

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
	void 메세지_전송(){
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

}
