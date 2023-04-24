package com.dplot.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dplot.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dplot.common.SOMap;
import com.dplot.mapper.UserLogMapper;
import com.dplot.mapper.UserTotalMapper;
import com.dplot.security.model.User;
import com.dplot.util.ServletRequestInfoUtil;

import net.sf.json.JSONObject;

@Component
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private UserTotalMapper userTotalMapper;
	
	@Autowired
	private UserLogMapper userLogMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		User authedUser = (User) authentication.getPrincipal();
		
//		SOMap userTotal = new SOMap();
//		userTotal.put("securecode", authedUser.getSecurecode());
//		userTotal.put("visitip", ServletRequestInfoUtil.getRequestIp());
//		userTotal.put("userno", authedUser.getNo());
//		userTotalMapper.updateUserTotal(userTotal);
		
		SOMap userLog = new SOMap();
		userLog.put("userno", authedUser.getNo());
		userLog.put("ip", ServletRequestInfoUtil.getRequestIp());
		userLogMapper.insertUserLog(userLog);

		JSONObject json = new JSONObject();
		json.put("no", authedUser.getNo());
		json.put("id", authedUser.getUserid());
		json.put("name", authedUser.getName());
		json.put("usertype", authedUser.getUsertype());
		json.put("status", Status.OK.getKey());

		response.setStatus(Status.OK.getKey());
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json.toString());
	}
}