package com.ashe.popping.domain.message.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.entity.Message;
import com.ashe.popping.domain.message.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;

	public Message saveMessage(MessageDto messageDto) {
		Message message = Message.from(messageDto);
		return messageRepository.save(message);
	}

}
