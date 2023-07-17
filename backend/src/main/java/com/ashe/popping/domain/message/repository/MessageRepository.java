package com.ashe.popping.domain.message.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashe.popping.domain.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	public List<Message> findByReceiver(Long receiver);
}
