package com.dplot.security.domain;

public enum Message {
	USER_OR_PWD_NOT_MATCH(1000,"아이디 또는 비밀번호가 일치하지 않습니다."),
	USER_OR_PWD_NOT_MATCH_OVERTIME(1003,"아이디 또는 비밀번호가 일치하지 않습니다. 입력시도가 5회 초과했습니다."),
	ORDER_NOT_FOUND(1001,"입력하신 정보와 일치하는 주문내역이 없습니다."),
	ID_WAIT_APPROVAL(1002,"승인 대기중인 아이디입니다.\n관리자 승인 후 이용이 가능합니다."),
	ID_REJECT(1004,"승인 거절된 아이디입니다.\n관리자 승인 후 이용이 가능합니다."),
	PARTNERS_DEALERST_STOP(1005,"일시중지 상태의 아이디 입니다.\n관리자 승인 후 이용이 가능합니다."),
	PARTNERS_DEALERST_CLOSED(1006,"휴점 처리된 아이디입니다.\n관리자 승인 후 이용이 가능합니다."),
	PARTNERS_DEALERST_EXIT(1007,"퇴점 처리된 아이디입니다.\n관리자 승인 후 이용이 가능합니다."),
	USER_SNS_NOT_MATCH(1008,"SNS로그인 정보가 일치하지 않습니다."),
	USER_BIO_NOT_MATCH(1008,"바이오로그인 정보가 일치하지 않습니다."),
	USER_STATUS_SLEEP(1009,"휴면 전환된 회원입니다.");
	
	private final int code;
	private final String msg;
	
	private Message(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
