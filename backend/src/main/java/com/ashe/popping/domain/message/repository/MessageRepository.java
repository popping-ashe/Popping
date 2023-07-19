package com.ashe.popping.domain.message.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashe.popping.domain.message.dto.MessageState;
import com.ashe.popping.domain.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findBySender(Long sender);

	List<Message> findByReceiverAndExpirationTimeAfterAndStateIs(Long receiver, LocalDateTime now, MessageState state);

	Long countByReceiverAndExpirationTimeBetweenAndStateIsNot(Long receiver, LocalDateTime start, LocalDateTime end,
		MessageState state);

	Message findByMessageId(Long messageId);
}
