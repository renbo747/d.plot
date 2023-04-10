package com.dplot.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.mapper.UserLogMapper;
import com.dplot.mapper.UserTotalMapper;
import com.dplot.security.model.Dealer;
import com.dplot.util.ServletRequestInfoUtil;

import net.sf.json.JSONObject;

@Component
public class DealerAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserTotalMapper userTotalMapper;

	@Autowired
	private UserLogMapper userLogMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

			Dealer authedDealer = (Dealer) authentication.getPrincipal();

//			SOMap userTotal = new SOMap();
//			userTotal.put("securecode", authedDealer.getSecurecode());
//			userTotal.put("visitip", ServletRequestInfoUtil.getRequestIp());
//			userTotal.put("userno", authedDealer.getUserno());
//			userTotalMapper.updateUserTotal(userTotal);

			SOMap userLog = new SOMap();
			userLog.put("userno", authedDealer.getUserno());
			userLog.put("ip", ServletRequestInfoUtil.getRequestIp());
			userLogMapper.insertUserLog(userLog);

			JSONObject json = new JSONObject();
			json.put("no", authedDealer.getUserno());
			json.put("id", authedDealer.getUserid());
			json.put("name", authedDealer.getName());
			json.put("usertype", authedDealer.getUsertype());
			json.put("dealerst", authedDealer.getDealerst());
			json.put("reqDealst", authedDealer.getReqdealst());
			json.put("chargename", authedDealer.getChargename());
			json.put("chargemobile", authedDealer.getChargemobile());
			json.put("templateCnt", authedDealer.getTemplatecnt());

			json.put("status", Status.OK.getKey());

			response.setStatus(Status.OK.getKey());
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json.toString());
	}
}