package com.ashe.popping.api.message.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashe.popping.api.message.dto.MessageApiDto;
import com.ashe.popping.domain.message.dto.MessageDto;
import com.ashe.popping.domain.message.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
	// 요청으로 몇시간 후 만료인지 시간이 들어옴...
	private final MessageService messageService;

	@PostMapping
	public ResponseEntity<MessageApiDto.Response> sendMessage(@RequestBody MessageApiDto.Request request) {
		MessageDto messageDto = messageService.saveMessage(MessageDto.from(request));
		return ResponseEntity.ok(MessageApiDto.Response.from(messageDto));
	}
}
