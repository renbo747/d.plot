package com.dplot.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.mapper.UserTotalMapper;
import com.dplot.security.model.Dealer;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DealerLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler{
	
	@Autowired
	private UserTotalMapper userTotalMapper;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
//		if (authentication != null && authentication.getPrincipal() != null) {
//			Dealer member = (Dealer) authentication.getPrincipal();
//			SOMap userTotal = new SOMap();
//			userTotal.put("userno", member.getUserno());
//			userTotalMapper.updateUserTotal(userTotal);
//		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Response res = new Response();
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(objectMapper.writeValueAsString(res));
	}

}