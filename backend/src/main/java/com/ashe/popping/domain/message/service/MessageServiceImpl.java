package com.ashe.popping.domain.message.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.dto.MessageState;
import com.ashe.popping.domain.message.entity.Message;
import com.ashe.popping.domain.message.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageRepository messageRepository;
	private final MemberService memberService;

	@Override
	public MessageDto saveMessage(MessageDto messageDto, MemberDto memberDto) {
		Message message = Message.of(messageDto, memberDto);
		messageRepository.save(message);
		return MessageDto.from(message);
	}

	@Override
	public List<MessageDto> loadReceiveMessage(Long receiver) {
		LocalDateTime now = LocalDateTime.now();
		List<Message> messages = messageRepository.findByReceiverAndExpirationTimeAfterAndStateIs(receiver,
			now, MessageState.UNREAD);
		MemberDto memberDto = MemberDto.of(receiver, now);
		memberService.updateLastVisitedTime(memberDto);
		return messages.stream()
			.map(MessageDto::from)
			.toList();
	}

	@Override
	public List<MessageDto> loadSendMessage(Long sender) {
		List<Message> messages = messageRepository.findBySender(sender);
		return messages.stream()
			.map(MessageDto::from)
			.toList();
	}

	@Override
	public Long countExpireMessage(Long receiver, LocalDateTime lastVisitedTime) {
		return messageRepository
			.countByReceiverAndExpirationTimeBetweenAndStateIsNot(receiver, lastVisitedTime, LocalDateTime.now(),
				MessageState.READ);
	}

	@Override
	public MessageDto updateMessageStateToRead(Long messageId) {
		Message message = messageRepository.findByMessageId(messageId);
		message.updateStateToRead();
		return MessageDto.from(message);
	}
}
