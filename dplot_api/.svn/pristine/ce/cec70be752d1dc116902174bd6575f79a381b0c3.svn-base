package com.dplot.exception;

import com.dplot.common.Status;

public class CustomException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String code;
    
	public CustomException(String message) {
		super(message);
		this.code = String.valueOf(Status.FAIL);
	}

	public CustomException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public CustomException(int code, String message) {
		super(message);
		this.code = String.valueOf(code);
	}

	public CustomException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public CustomException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
}