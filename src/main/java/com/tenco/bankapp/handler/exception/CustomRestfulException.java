package com.tenco.bankapp.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomRestfulException extends RuntimeException{
	
	private HttpStatus status;
	
	public CustomRestfulException(String message, HttpStatus httpStatus) {
		super(message);
		this.status = httpStatus; 
	}

}
