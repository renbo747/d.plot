package com.dplot.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.CookieGenerator;

import com.dplot.common.CMConst;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.security.domain.Message;
import com.dplot.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		Response result = new Response(Status.FAIL, exception.getMessage());
		result.setErrorShow(false);
		
		if (Message.USER_OR_PWD_NOT_MATCH_OVERTIME.getMsg().equals(exception.getMessage())) {
			CookieGenerator cg = new CookieGenerator();
			cg.setCookieDomain(Util.getMainDomain(request.getHeader("origin")));
			cg.setCookiePath("/");
			cg.setCookieName(CMConst.COOKIE_OVER_TIME_LOGIN_ATTEMPT);
			cg.setCookieMaxAge(365*24*60*60);
			cg.addCookie(response, "OVERTIMELOGIN");
		} else if(Message.USER_STATUS_SLEEP.getMsg().equals(exception.getMessage())) {
			SOMap map = new SOMap();
			map.put("isSleep", true);
			
			result.setData(map);
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		String data = objectMapper.writeValueAsString(result);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();
		out.close();
	}
	
}
