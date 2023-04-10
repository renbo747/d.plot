package com.dplot.front.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.front.service.FrontGoodsService;


/**
 * 사용자 상품Controller <BR>
 * 사용자에서 상품메뉴
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);	
	
	/** The gs. */
	@Autowired
	private FrontGoodsService fgs;
	
	/**
	 * 사용자 상품 상세
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value="/detail",  method = RequestMethod.POST)
	public Response goodsDetail(@RequestBody SOMap params, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Response res = new Response();
		res.setErrorShow(false);
		
		res.setData(fgs.goodsDetail(params));
		
		return res;
		
		
	}
	
	/**
	 * 쿠폰 조회
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value="/coupon",  method = RequestMethod.POST)
	public Response coupon(@RequestBody SOMap params, HttpServletRequest request, HttpServletResponse response) throws Exception{	
		return fgs.getCoupon(params);
	}
	
	/**
	 * 상품 하트
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value="/wish",  method = RequestMethod.POST)
	public Response wish(@RequestBody SOMap params, HttpServletRequest request, HttpServletResponse response) throws Exception{	
		return fgs.wish(params);
	}
	
	/**
	 * 상품 위시리스트 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/wishList", method = RequestMethod.POST)
	public Response goodsLikeList(@RequestBody SOMap params) throws Exception {
		return new Response(fgs.selectWishList(params));
	}
	
	/**********************
	 * 콘텐츠 좋아요 전체 삭제 처리
	 ********************/
	@RequestMapping(value = "/wishDel",method = RequestMethod.POST)
	public Response delAllLike(@RequestBody SOMap param) throws Exception {
		return new Response(fgs.deleteWishLike(param));
	}
	
	/*********************
	 * 최근본 상품 목록 조회
	 ********************/
	@RequestMapping(value = "/recentList", method = RequestMethod.POST)
	public Response recentList(@RequestBody SOMap param) throws Exception {
		return new Response(fgs.selectRecentList(param));
	}
	
	/*********************
	 * 베스트 상품 목록 조회
	 ********************/
	@RequestMapping(value = "/bestList", method = RequestMethod.POST)
	public Response bestList(@RequestBody SOMap param) throws Exception {
		return new Response(fgs.selectBestList(param));
	}

	/**
	 * 추가 상품
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value="/addGoods",  method = RequestMethod.POST)
	public Response addGoods(@RequestBody SOMap params) throws Exception{
		Response res = new Response();
		res.setData(fgs.selectAddGoodsList(params));
		res.setErrorShow(false);
		
		return res;
	}
	
	/**
	 * 옵션 상세
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value="/option",  method = RequestMethod.POST)
	public Response option(@RequestBody SOMap params) throws Exception{
		Response res = new Response();
		res.setData(fgs.selectOptionList(params));
		res.setErrorShow(false);
		
		return res;
	}
	
	/**
	 * 옵션 상세
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value="/optionDetail",  method = RequestMethod.POST)
	public Response optionDetail(@RequestBody SOMap params) throws Exception{
		Response res = new Response();
		res.setData(fgs.selectOptionDetailList(params));
		res.setErrorShow(false);
		
		return res;
	}
	
	
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Response goodsList(@RequestBody SOMap params) throws Exception {
		return new Response(fgs.selectGoodsListByCateIdx(params));
	}
	
	/**
	 * 재입고알림 신청 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/restockList", method = RequestMethod.POST)
	public Response restockList(@RequestBody SOMap param) throws Exception {
		return new Response(fgs.selectRestockList(param));
	}
	
	/**
	 * 재입고알림 신청 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/restock/apply", method = RequestMethod.POST)
	public Response insertRestock(@RequestBody SOMap param) throws Exception {
		return new Response(fgs.insertReware(param));
	}
	
	/**
	 * 재입고알림 신청 목록 삭제 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/restock/del", method = RequestMethod.POST)
	public Response delRestock(@RequestBody SOMap param) throws Exception {
		return new Response(fgs.deletelRestock(param));
	}
	
	/**
	 * 필터 상품목록 조회 목록의 좋아요 여부 가져오기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/filter/iswished", method = RequestMethod.POST)
	public Response selectIswished(@RequestBody SOMap param) throws Exception {
		return new Response(fgs.selectIswished(param));
	}
}
