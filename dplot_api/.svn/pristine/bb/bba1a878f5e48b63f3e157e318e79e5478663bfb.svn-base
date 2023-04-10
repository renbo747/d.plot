package com.dplot.security.token;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * <pre>
 * class	: TokenSimpleUrlAuthenticationSuccessHandler.java
 * 설명		: 기본 클래스 의 defaultTargetUrl 속성을 설정
 * </pre>
 * 
 * @Date	: 2018. 12. 21.
 */
public class TokenSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		String context = request.getContextPath();
		String fullURL = request.getRequestURI();
		String url = fullURL.substring(fullURL.indexOf(context)+context.length());
		return url;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String url = determineTargetUrl(request,response);
		request.getRequestDispatcher(url).forward(request, response);
	}

}