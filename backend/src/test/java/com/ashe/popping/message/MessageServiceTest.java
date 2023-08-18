package com.ashe.popping.message;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.ashe.popping.api.message.dto.MessageApiDto;
import com.ashe.popping.domain.member.constant.Role;
import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.entity.Member;
import com.ashe.popping.domain.member.repository.MemberRepository;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.domain.message.dto.MessageCountDto;
import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.dto.MessageState;
import com.ashe.popping.domain.message.entity.Message;
import com.ashe.popping.domain.message.repository.MessageRepository;
import com.ashe.popping.domain.message.service.MessageService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageServiceTest {

	@Autowired
	MessageService messageService;

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberService memberService;

	@Container
	static MariaDBContainer mariaDBContainer = new MariaDBContainer()
		.withDatabaseName("popping");

	@Test
	@DisplayName("save member")
	@Order(1)
	void saveMember() {
		Member member1 = Member.builder()
			.memberId(1L)
			.shareId(1234567890L)
			.kakaoId("12345678")
			.nickname("test1")
			.createdTime(LocalDateTime.now())
			.lastVisitedTime(LocalDateTime.now())
			.role(Role.USER)
			.build();
		Member member2 = Member.builder()
			.memberId(2L)
			.shareId(2345678901L)
			.kakaoId("23456781")
			.nickname("test2")
			.createdTime(LocalDateTime.now())
			.lastVisitedTime(LocalDateTime.now())
			.role(Role.USER)
			.build();
		memberRepository.save(member1);
		memberRepository.save(member2);
	}

	@Test
	@DisplayName("send message")
	@Order(2)
	void saveMessage() {
		// send message null to test1
		MessageApiDto.Request request = MessageApiDto.Request.builder()
			.content("content")
			.sender(null)
			.shareId(1234567890L)
			.nickname("sender")
			.retentionTime(24L)
			.build();
		MemberDto memberDto = MemberDto.builder()
			.memberId(1L)
			.shareId(1234567890L)
			.build();
		MessageDto messageDto = MessageDto.from(request);
		MessageDto savedMessage = messageService.saveMessage(messageDto, memberDto);
		Assertions.assertEquals(1L, savedMessage.getMessageId());
		Assertions.assertEquals(MessageState.UNREAD, savedMessage.getState());
		Assertions.assertEquals(messageDto.getContent(), savedMessage.getContent());
		Assertions.assertNull(savedMessage.getSender());

		//send message test1 to test2
		MessageApiDto.Request request2 = MessageApiDto.Request.builder()
			.content("content2")
			.sender(1L)
			.shareId(2345678901L)
			.nickname("test1")
			.retentionTime(24L)
			.build();
		MemberDto memberDto2 = MemberDto.builder()
			.memberId(2L)
			.shareId(2345678901L)
			.build();
		MessageDto messageDto2 = MessageDto.from(request2);
		messageService.saveMessage(messageDto2, memberDto2);
	}

	@Test
	@DisplayName("load Receive Message")
	@Order(3)
	void loadReceiveMessage() {
		Pageable pageable = PageRequest.of(0,20);
		List<MessageDto> messageDtos = messageService.loadReceiveMessage(1L, pageable);
		Assertions.assertEquals(1, messageDtos.size());
		Assertions.assertEquals("content", messageDtos.get(0).getContent());
		Assertions.assertEquals("sender", messageDtos.get(0).getNickname());
	}

	@Test
	@DisplayName("load Send Message")
	@Order(4)
	void loadSendMessage() {
		Pageable pageable = PageRequest.of(0,20);
		List<MessageDto> messageDtos = messageService.loadSendMessage(1L, pageable);
		Assertions.assertEquals(1, messageDtos.size());
		Assertions.assertEquals("content2", messageDtos.get(0).getContent());
		Assertions.assertEquals("test2",messageDtos.get(0).getReceiverNickname());
		Assertions.assertEquals("test1", messageDtos.get(0).getNickname());
	}

	@Test
	@Order(5)
	void updateMessageStateToRead() {
		messageService.updateMessageStateToRead(1L);
		Message message = messageRepository.findByMessageId(1L);
		Assertions.assertEquals(MessageState.READ, message.getState());
	}
	@Test
	void countMessagesByType(){
		MessageCountDto messageCountDto = messageService.countMessagesByType(1L);
		Assertions.assertEquals(1L, messageCountDto.getReceivedMessagesCount());
		Assertions.assertEquals(1L, messageCountDto.getSentMessagesCount());
		Assertions.assertEquals(0L, messageCountDto.getUnreadMessagesCount());
	}

	// @Test
	// @DisplayName("만료된 메시지의 갯수를 불러온다.")
	// void countExpireMessage() {
	// 	테스트_메시지_넣기();
	// 	Long msgCount = messageService.countExpireMessage(1L, LocalDateTime.now().minusDays(2L));
	// 	Assertions.assertThat(msgCount).isEqualTo(5);
	// }

	// void 테스트_메세지_넣기(){
	// 	for (int i = 1; i <= 5; i++) {
	// 		MessageDto messageDto = MessageDto.builder()
	// 			.state(MessageState.UNREAD)
	// 			.content("테스트 코드" + i)
	// 			.sender((long)i)
	// 			.receiver(1L)
	// 			.nickname("테스트" + i)
	// 			.expirationTime(LocalDateTime.now().minusDays(1L))
	// 			.build();
	// 		messageService.saveMessage(messageDto);
	// 	}
	// 	for (int i = 6; i <= 10; i++) {
	// 		MessageDto messageDto = MessageDto.builder()
	// 			.state(MessageState.UNREAD)
	// 			.content("테스트 코드" + i)
	// 			.sender(1L)
	// 			.receiver((long)(i - 5))
	// 			.nickname("테스트" + i)
	// 			.expirationTime(LocalDateTime.now().plusDays(2L))
	// 			.build();
	// 		messageService.saveMessage(messageDto);
	// 	}
	// }

}
