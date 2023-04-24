package com.dplot.front.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.annontation.FrontInfo;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.exception.BizException;
import com.dplot.front.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 결재할 주문목록 조회
	 */
	@FrontInfo
	@RequestMapping(value="/list",  method = RequestMethod.POST)
	public Response list(@RequestBody SOMap params) throws Exception {
		Response res = new Response();
		res.setData(orderService.selectOrderList(params));
		
		return res;
	}
	
	/**
	 * 결재할 주문번호 조회
	 */
	@RequestMapping(value="/orderno",  method = RequestMethod.POST)
	public Response orderno(@RequestBody SOMap params) throws Exception {
		Response res = new Response();
		res.setData(orderService.selectOrderNo(params));
		
		return res;
	}
	
	/**
	 * 주문정보 저장
	 */
	@RequestMapping(value="/save",  method = RequestMethod.POST)
	public Response saveOrder(@RequestBody SOMap params) throws Exception {
		Response res = new Response();
		res.setErrorShow(false);
		res.setData(orderService.saveOrder(params));
		
		return res;
	}
	
	/**
	 * 주문결재 계산처리
	 */
	@RequestMapping(value="/cal",  method = RequestMethod.POST)
	public Response cal(@RequestBody SOMap params) throws Exception {
		Response res = new Response();
		res.setData(orderService.calOrderAmt(params));
		
		return res;
	}
	
	/**
	 * 주문/결재 정보 조회
	 */
	@RequestMapping(value="/detail",  method = RequestMethod.POST)
	public Response detail(@RequestBody SOMap params) throws Exception {
		Response res = new Response();
		res.setErrorShow(false);
		res.setData(orderService.selectOrderDetail(params));
		
		return res;
	}
	
	/**
     * 회원가입 임직원 확인 인증번호 메일발송
     * @return Response
     * @throws Exception
     */
	@RequestMapping(value="/sendAuth", method=RequestMethod.POST)
	public Response sendAuth(@RequestBody SOMap param) throws Exception{
		Response res = new Response(orderService.sendAuth(param));
		return res;
		
	}
	
	/**
     * 회원가입 임직원 확인 인증번호 확인
     * @return Response
     * @throws Exception
     */
	@RequestMapping(value="/confirmAuth", method=RequestMethod.POST)
	public Response confirmAuth(@RequestBody SOMap param) throws Exception{
		Response res = new Response();
		
		orderService.confirmAuth(param);
		
		return res;
	}
	
	/**
     * 회원가입 임직원 확인 인증번호 확인
     * @return Response
     * @throws Exception
     */
	@RequestMapping(value="/card/promotion", method=RequestMethod.POST)
	public Response cardPromotion(@RequestBody SOMap param) throws Exception{
		Response res = new Response();
		
		orderService.cardPromotion(param);
		
		return res;
	}
}
