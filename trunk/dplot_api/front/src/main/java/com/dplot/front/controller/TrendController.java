package com.dplot.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.front.service.TrendService;

@RestController
@RequestMapping("/trend")
public class TrendController {
	@Autowired
	private  TrendService trendService;
	
	/**
	 * 매거진 트렌드 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list" , method = RequestMethod.POST)
	public Response selectTrendList(@RequestBody SOMap param) throws Exception {
		return new Response(trendService.selectTrendList(param));
	}
	
	/**
	 * 매거진 트렌드 상세 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail" , method = RequestMethod.POST)
	public Response detail(@RequestBody SOMap param) throws Exception {
		return new Response(trendService.selectTrendDetail(param));
	}
	/**
	 * 매거진 트렌드 좋아요 변경
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wish" , method = RequestMethod.POST)
	public Response wish(@RequestBody SOMap param) throws Exception {
		return new Response(trendService.changeWish(param));
	}
	
}
