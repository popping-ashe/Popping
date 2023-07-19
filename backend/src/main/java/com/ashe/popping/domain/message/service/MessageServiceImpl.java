package com.ashe.popping.domain.message.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	public MessageDto saveMessage(MessageDto messageDto) {
		Message message = Message.from(messageDto);
		messageRepository.save(message);
		return MessageDto.from(message);
	}

	@Override
	public List<MessageDto> loadReceiveMessage(Long receiver) {
		List<Message> messages = messageRepository.findByReceiverAndExpirationTimeAfter(receiver, LocalDateTime.now());
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
}
