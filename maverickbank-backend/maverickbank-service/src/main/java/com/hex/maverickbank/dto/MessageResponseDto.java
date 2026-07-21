package com.hex.maverickbank.dto;

import org.springframework.stereotype.Component;

@Component
public class MessageResponseDto {
	private String body;
	private int statusCode;
	
	public MessageResponseDto(String body, int statusCode) {
		super();
		this.body = body;
		this.statusCode = statusCode;
	}
	
	

	public MessageResponseDto() {
		super();
	}



	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	 

}
