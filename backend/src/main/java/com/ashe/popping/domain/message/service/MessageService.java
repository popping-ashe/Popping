package com.ashe.popping.domain.message.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.message.dto.MessageCountDto;
import com.ashe.popping.domain.message.dto.MessageDto;

public interface MessageService {

	MessageDto saveMessage(MessageDto messageDto, MemberDto memberDto);

	MessageDto saveReplyMessage(MessageDto messageDto);

	List<MessageDto> loadReceiveMessage(Long receiver, Pageable pageable);

	List<MessageDto> loadSendMessage(Long sender, Pageable pageable);

	Long countExpireMessage(Long receiver, LocalDateTime lastVisitedTime);

	MessageDto updateMessageStateToRead(Long messageId);

	void updateMessageStateToExpired(Long messageId);

	MessageCountDto countMessagesByType(Long memberId);
}
