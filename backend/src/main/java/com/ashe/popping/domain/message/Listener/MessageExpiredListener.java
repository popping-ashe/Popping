package com.ashe.popping.domain.message.Listener;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.ashe.popping.domain.message.service.MessageService;

@Component
public class MessageExpiredListener extends KeyExpirationEventMessageListener {

	private MessageService messageService;

	@Autowired
	public MessageExpiredListener(RedisMessageListenerContainer listenerContainer, MessageService messageService) {
		super(listenerContainer);
		this.messageService = messageService;
	}

	public MessageExpiredListener(@Qualifier("redisMessageListenerContainer") RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String[] messageArr = message.toString().split("-");
		if (!messageArr[0].equals("messageId")) {
			return;
		}
		messageService.updateMessageStateToExpired(Long.parseLong(messageArr[1]));
	}
}
