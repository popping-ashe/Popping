package com.ashe.popping.domain.message.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ashe.popping.domain.message.dto.MessageState;
import com.ashe.popping.domain.message.entity.Message;

import jakarta.transaction.Transactional;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findBySender(Long sender, Pageable pageable);

	List<Message> findByReceiverAndExpirationTimeAfter(Long receiver, LocalDateTime now, Pageable pageable);

	Long countByReceiverAndExpirationTimeBetweenAndStateIsNot(Long receiver, LocalDateTime start, LocalDateTime end,
		MessageState state);

	Message findByMessageId(Long messageId);

	Long countByReceiver(Long receiver);

	Long countBySender(Long sender);

	Long countByReceiverAndStateIs(Long receiver, MessageState state);

}
