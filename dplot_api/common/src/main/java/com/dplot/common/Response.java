package com.dplot.common;

import java.util.Map;

public class Response {
	
	private int statusCode;
	
	private String message;
	
	private Map<String, Object> data;

	private boolean errorShow = true;
	
	public Response() {
		super();
		this.statusCode = Status.OK.getKey();
		this.message = Status.OK.getValue();
	}
	
	public Response(Status status) {
		super();
		this.statusCode = status.getKey();
		this.message = status.getValue();
	}
	
	public Response(Status status, String message) {
		super();
		this.statusCode = status.getKey();
		this.message = message;
	}
	
	public Response(Map<String, Object> data) {
		super();
		this.statusCode = Status.OK.getKey();
		this.message = Status.OK.getValue();
		this.data = data;
	}
	
	public Response(Status status, Map<String, Object> data) {
		super();
		this.statusCode = status.getKey();
		this.message = status.getValue();
		this.data = data;
	}
	
	public Response(Status status, String message, Map<String, Object> data) {
		super();
		this.statusCode = status.getKey();
		this.message = message;
		this.data = data;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public boolean isErrorShow() {
		return errorShow;
	}

	public void setErrorShow(boolean errorShow) {
		this.errorShow = errorShow;
	}
}
