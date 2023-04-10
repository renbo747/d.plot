package com.dplot.security.domain;

public enum RoleName {
	
	ROLE_DEALER(8, "ROLE_DEALER"),
	ROLE_ADMIN(1, "ROLE_ADMIN"),
	ROLE_MEMBER(16, "ROLE_MEMBER"),
	ROLE_NON_MEMBER(32,"ROLE_NON_MEMBER");
	
	private final int code;
	private final String name;
	
	private RoleName(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
