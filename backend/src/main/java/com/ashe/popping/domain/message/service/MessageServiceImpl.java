package com.ashe.popping.domain.message.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.entity.Message;
import com.ashe.popping.domain.message.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

	private final MessageRepository messageRepository;

	public MessageDto saveMessage(MessageDto messageDto) {
		Message message = Message.from(messageDto);
		messageRepository.save(message);
		return MessageDto.from(message);
	}

	public List<MessageDto> loadReceiveMessage(Long receiver) {
		List<Message> messages = messageRepository.findByReceiverAndExpirationTimeBefore(receiver, LocalDateTime.now());
		return messages.stream()
			.map(MessageDto::from)
			.toList();
	}

	public List<MessageDto> loadSenderMessage(Long sender) {
		List<Message> messages = messageRepository.findBySender(sender);
		return messages.stream()
			.map(MessageDto::from)
			.toList();
	}
}
