package com.minhduc.tuto.spring.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minhduc.tuto.spring.domain.Message;

@RestController
public class HelloWorldRestController {

	@RequestMapping("/")
	public String welcome(){ // Welcome page, non-rest
		return "Welcome to RestTemple Example";
	}
	
	@RequestMapping("/hello/{player}")
	public Message message(@PathVariable String player){
		Message message = new Message(player, "Hello " + player);
		return message;
	}
}
