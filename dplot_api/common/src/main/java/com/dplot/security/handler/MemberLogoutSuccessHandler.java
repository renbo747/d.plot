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

import com.dplot.common.CMConst;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.mapper.UserTotalMapper;
import com.dplot.security.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MemberLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler{
	
	@Autowired
	private UserTotalMapper userTotalMapper;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
//		if (authentication != null && authentication.getPrincipal() != null) {
//			Member member = (Member) authentication.getPrincipal();
//			SOMap userTotal = new SOMap();
//			userTotal.put("userno", member.getUserno());
//			userTotalMapper.updateUserTotal(userTotal);
//		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		SOMap map = new SOMap();
		map.put("authType", "nonMember");
		map.put("userid", CMConst.GUEST_ID);
		map.put("userNo", 0);
		map.put("memberType", "");
		map.put("joinchtype", "");
		map.put("name", "비회원");
		
		Response res = new Response(map);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(objectMapper.writeValueAsString(res));
	}

}