package com.anticorruption.user.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

	private int status;
	
	private String message;
	
	private LocalDateTime timeStamp;
	
	public ErrorResponse(int status, String message, LocalDateTime timeStamp) {
		this.status = status;
		this.message = message;
		this.timeStamp = timeStamp;
	}
}
