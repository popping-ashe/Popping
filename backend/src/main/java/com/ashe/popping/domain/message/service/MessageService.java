package com.ashe.popping.domain.message.service;

import java.util.List;

import com.ashe.popping.domain.message.dto.MessageDto;

public interface MessageService {

	MessageDto saveMessage(MessageDto messageDto);
	List<MessageDto> loadReceiveMessage(Long receiver);
	List<MessageDto> loadSenderMessage(Long sender);
}
