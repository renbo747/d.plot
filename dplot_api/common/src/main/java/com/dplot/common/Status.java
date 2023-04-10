package com.dplot.common;

public enum Status {
	OK(200, "정상 처리되었습니다." ),
	CREATED(201, "정상 처리되었습니다." ),
	PROC_FAIL(204, "처리중 오류가 발생하였습니다." ),
	FAIL(500, "서버에 오류가 발생하였습니다." ),
	BAD_REQUEST(400, "잘못된 요청입니다." ),
	UNAUTHORIZED(401, "로그인 권한이 없습니다." ),
	FORBIDDEN(403, "접근권한이 없습니다." ),
	NOT_FOUND(404, "요청한 경로를 찾을수 없습니다." );
	
	private int status;
	private String message;
	
	Status(int status, String message) {
		this.status = status;
		this.message = message;
	}
    
	public int getKey() {
		return this.status;
	}

	public String getValue() {
		return this.message;
	}
}
