package com.ashe.popping.api.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.domain.message.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {
	// 요청으로 몇시간 후 만료인지 시간이 들어옴...
	private final MessageService messageService;

	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

}
