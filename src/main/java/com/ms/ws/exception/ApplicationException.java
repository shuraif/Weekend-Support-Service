package com.ms.ws.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException{
	
	private HttpStatus errorCode;
	
	private String errorMessage;
	
	public ApplicationException(HttpStatus errorCode,String errorMessage){
		super();
		this.errorCode=errorCode;
		this.errorMessage=errorMessage;
	}

	public HttpStatus getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(HttpStatus errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
}
