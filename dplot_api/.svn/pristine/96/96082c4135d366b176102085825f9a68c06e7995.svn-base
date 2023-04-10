package com.dplot.security.token;

import org.springframework.security.core.GrantedAuthority;

/**
 * <pre>
 * class	: RoleAdapter.java
 * 설명		: 
 * </pre>
 * 
 * @Date	: 2018. 12. 21.
 */
public class RoleAdapter implements GrantedAuthority{
	final String role;
	public RoleAdapter (String role) {
		this.role=role;
	}
	@Override
	public String getAuthority() {
		return role;
	}

}