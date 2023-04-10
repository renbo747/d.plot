package com.dplot.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.service.CJMessageService;
import com.dplot.front.service.EtcService;

@RestController
@RequestMapping("etc")
public class EtcController {
	@Autowired
	private EtcService etcservice;
	
	@Autowired
	private CJMessageService cjMessageService; 
	
	/**
	 * 구독신청하기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/subscribe", method=RequestMethod.POST)
	public Response sendSubscribe(@RequestBody SOMap param) throws Exception {
		return new Response(etcservice.sendSubscribe(param));
	}
	
	/**
	 * 구독취소처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/subscribeCancel", method=RequestMethod.POST)
	public Response subscribeCancel(@RequestBody SOMap param) throws Exception {
		return new Response(etcservice.subscribeCancel(param));
	}
	
	/**
	 * 이용약관 이력 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/useTerms", method=RequestMethod.POST)
	public Response useTerms(@RequestBody SOMap param) throws Exception {
		return new Response(etcservice.useTerms(param));
	}
	
	/**
	 * 입점업체 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/dealer", method=RequestMethod.POST)
	public Response dealerList(@RequestBody SOMap param) throws Exception {
		return new Response(etcservice.dealerList(param));
	}
	
	/**********************
	 * 친구초대 리워드 보상정보 조회
	 ********************/
	@RequestMapping(value = "/reward", method = RequestMethod.POST)
	public Response selectRewardInfo(@RequestBody SOMap param) throws Exception {
		return new Response(etcservice.selectRewardInfo(param));
	}
	
	/**
	 * as 신청 완료 시 알림톡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/sendapply", method = RequestMethod.POST)
	public Response sendApply(@RequestBody SOMap param) throws Exception {
		SOMap result = new SOMap();
		cjMessageService.sendAsApply(param);

		return new Response(result);
	}
}
