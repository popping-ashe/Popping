package com.ashe.popping.domain.message.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.message.dto.MessageDto;

public interface MessageService {

	MessageDto saveMessage(MessageDto messageDto, MemberDto memberDto);

	List<MessageDto> loadReceiveMessage(Long receiver);

	List<MessageDto> loadSendMessage(Long sender);

	Long countExpireMessage(Long receiver, LocalDateTime lastVisitedTime);

	MessageDto updateMessageStateToRead(Long messageId);
}
