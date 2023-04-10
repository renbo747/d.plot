package com.dplot.security.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * <pre>
 * class	: JWTAuthenticationToken.java
 * 설명		: JWTToken 파싱
 * </pre>
 * 
 * @Date	: 2018. 12. 21.
 */
@SuppressWarnings({"rawtypes","unchecked","unused"})
public class JWTAuthenticationToken extends AbstractAuthenticationToken{
	private static final long serialVersionUID = 1L;
	private final Object principal;
	private Object details;

	Collection authorities;
	public JWTAuthenticationToken(String jwtToken) {
		super(null);
		super.setAuthenticated(true);
		JWTParser parser = new JWTParser(jwtToken);
		
		this.principal=parser.getSub();
		this.setDetailsAuthorities();
	}

	@Override
	public Object getCredentials() {
		return "";
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}
	private void setDetailsAuthorities() {
		String username = principal.toString();
		SpringUserDetailsAdapter adapter = new SpringUserDetailsAdapter(username);
		details=adapter;
		authorities=adapter.getAuthorities();
	}

	@Override
	public Collection getAuthorities() {
		return authorities;
	}

}