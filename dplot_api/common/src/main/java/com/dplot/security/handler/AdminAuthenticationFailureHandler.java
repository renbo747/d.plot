package com.dplot.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.dplot.common.Response;
import com.dplot.common.Status;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AdminAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		Response result = new Response(Status.FAIL, exception.getMessage());
		
		ObjectMapper objectMapper = new ObjectMapper();
		String data = objectMapper.writeValueAsString(result);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();
		out.close();
	}
	
}
