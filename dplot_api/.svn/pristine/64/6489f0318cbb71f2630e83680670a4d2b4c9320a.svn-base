package com.dplot.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.dplot.common.Response;
import com.dplot.common.Status;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {
    	
    	response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		Response result = null; 
		
    	if(exc instanceof AccessDeniedException) {
    		result = new Response(Status.UNAUTHORIZED);
    	} else {
    		result = new Response(Status.FAIL, exc.getMessage());
    	}

    	ObjectMapper objectMapper = new ObjectMapper();
		String data = objectMapper.writeValueAsString(result);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();
		out.close();
    }
}