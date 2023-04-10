package com.dplot.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.front.service.MzService;

@RestController
@RequestMapping("/mz")
public class MzController {
	@Autowired
	private MzService mzservice;
	
	/**********************
	 * 콘텐츠 좋아요 목록 조회(브랜드, 트랜드)
	 ********************/
	@RequestMapping(value = "/likeList",method = RequestMethod.POST)
	public Response selectTrendLikeList(@RequestBody SOMap param) throws Exception {
		return new Response(mzservice.selectTrendLikeList(param));
	}
	
	/**********************
	 * 콘텐츠 좋아요 전체 삭제 처리
	 ********************/
	@RequestMapping(value = "/likedelAll",method = RequestMethod.POST)
	public Response delAllLike(@RequestBody SOMap param) throws Exception {
		return new Response(mzservice.deleteTrendLikeAll(param));
	}
	
	/**********************
	 * 콘텐츠 좋아요 삭제 처리
	 ********************/
	@RequestMapping(value = "/likedel",method = RequestMethod.POST)
	public Response dellike(@RequestBody SOMap param) throws Exception {
		return new Response(mzservice.deleteTrendLike(param));
	}
	
	/**********************
	 * Magazine 메인 목록 조회
	 ********************/
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public Response selectMzList(@RequestBody SOMap param) throws Exception {
		return new Response(mzservice.selectMzList(param));
	}
	
	/**********************
	 * Magazine Keyword 목록 조회
	 ********************/
	@RequestMapping(value = "/keyword",method = RequestMethod.POST)
	public Response selectMzKeywordList(@RequestBody SOMap param) throws Exception {
		return new Response(mzservice.selectMzKeywordList(param));
	}
	
	/**********************
	 * Magazine Popup 목록 조회
	 ********************/
	@RequestMapping(value = "/popup",method = RequestMethod.POST)
	public Response selectPopupList(@RequestBody SOMap param) throws Exception {
		return new Response(mzservice.selectPopupList(param));
	}
}
