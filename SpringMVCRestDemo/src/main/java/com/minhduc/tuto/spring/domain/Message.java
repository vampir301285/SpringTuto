package com.minhduc.tuto.spring.domain;

public class Message {

	private String name;
	private String text;

	public Message(String name, String text) {
		super();
		this.name = name;
		this.text = text;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

}
