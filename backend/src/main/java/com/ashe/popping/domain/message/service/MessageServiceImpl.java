package com.ashe.popping.domain.message.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashe.popping.domain.member.dto.MemberDto;
import com.ashe.popping.domain.member.service.MemberService;
import com.ashe.popping.domain.message.dto.MessageCountDto;
import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.dto.MessageRedisDto;
import com.ashe.popping.domain.message.dto.MessageState;
import com.ashe.popping.domain.message.entity.Message;
import com.ashe.popping.domain.message.repository.MessageRedisRepository;
import com.ashe.popping.domain.message.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageRedisRepository messageRedisRepository;

	private final MessageRepository messageRepository;
	private final MemberService memberService;

	@Transactional
	@Override
	public MessageDto saveMessage(MessageDto messageDto, MemberDto memberDto) {
		Message message = Message.of(messageDto, memberDto);
		messageRepository.save(message);
		messageRedisRepository.save(MessageRedisDto.of(message.getMessageId(), message.getExpirationTime()));
		return MessageDto.from(message);
	}

	@Transactional
	@Override
	public MessageDto saveReplyMessage(MessageDto messageDto) {
		Message message = messageRepository.findByMessageId(messageDto.getMessageId());
		Message newMessage = messageRepository.save(Message.of(messageDto, message));
		messageRedisRepository.save(MessageRedisDto.of(newMessage.getMessageId(), newMessage.getExpirationTime()));
		return MessageDto.from(newMessage);
	}

	@Transactional(readOnly = true)
	@Override
	public List<MessageDto> loadReceiveMessage(Long receiver, Pageable pageable) {
		LocalDateTime now = LocalDateTime.now();
		List<Message> messages = messageRepository.findByReceiverAndExpirationTimeAfter(receiver, now, pageable);
		MemberDto memberDto = MemberDto.of(receiver, now);
		memberService.updateLastVisitedTime(memberDto);
		return messages.stream()
			.map(MessageDto::from)
			.toList();
	}

	@Override
	public List<MessageDto> loadSendMessage(Long sender, Pageable pageable) {
		List<Message> messages = messageRepository.findBySender(sender, pageable);
		return messages.stream()
			.map(
				m -> {
					String nickname = m.getReceiverNickname();
					if (nickname == null){
						try {
							MemberDto optionalMemberDto = memberService.getMemberByMemberId(m.getReceiver());
							nickname = optionalMemberDto.getNickname();
						} catch (Exception e) {
							nickname = "탈퇴한 회원";
						}
					}
					return MessageDto.of(m, nickname);
				}
			).toList();
	}

	@Transactional(readOnly = true)
	@Override
	public Long countExpireMessage(Long receiver, LocalDateTime lastVisitedTime) {
		return messageRepository
			.countByReceiverAndExpirationTimeBetweenAndStateIsNot(receiver, lastVisitedTime, LocalDateTime.now(),
				MessageState.READ);
	}

	@Transactional
	@Override
	public MessageDto updateMessageStateToRead(Long messageId) {
		Message message = messageRepository.findByMessageId(messageId);
		message.updateStateToRead();
		return MessageDto.from(message);
	}

	@Transactional
	@Override
	public void updateMessageStateToExpired(Long messageId) {
		Message message = messageRepository.findByMessageId(messageId);
		if (!message.getState().equals(MessageState.UNREAD))
			return;
		message.updateStateToExpired();
	}

	@Transactional(readOnly = true)
	@Override
	public MessageCountDto countMessagesByType(Long memberId) {
		Long receivedMessagesCount = messageRepository.countByReceiver(memberId);
		Long sentMessagesCount = messageRepository.countBySender(memberId);
		Long expiredMessagesCount = messageRepository.countByReceiverAndStateIs(memberId, MessageState.EXPIRED);
		return MessageCountDto.of(receivedMessagesCount, sentMessagesCount, expiredMessagesCount);
	}
}
