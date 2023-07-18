package com.ashe.popping.domain.message.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashe.popping.domain.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findByReceiverAndExpirationTimeBefore(Long receiver, LocalDateTime expirationTime);

	List<Message> findBySender(Long sender);
}
