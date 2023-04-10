/**
 * 
 */
package com.dplot.front.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.front.service.CartService;

/**
 * @FileName : CartController.java
 * @Project : datapick_api
 * @Date : 2021. 12. 8. 
 * @Author : KTH
 * ============================================================
 *  DATE               AUTHOR         NOTE
 * ============================================================
 *  2021. 12. 8.         KTH                 최초작성
 * ------------------------------------------------------------
 **/
@RestController
@RequestMapping("cart")
public class CartController {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);	
	
	/** The gs. */
	@Autowired
	private CartService cartService;
	
	/**
	 * 장바구니 개수 조회
	 * 
	 */
	@RequestMapping(value="/cnt",  method = RequestMethod.POST)
	public Response cartCnt(@RequestBody SOMap params) throws Exception {
		Response res = new Response();
		res.setData(cartService.selectCartCnt(params));
		
		return res;
	}
	
	/**
	 * 장바구니 저장
	 * @throws Exception
	 */
	@RequestMapping(value="/save",  method = RequestMethod.POST)
	public Response cartSave(@RequestBody SOMap params) throws Exception {
		Response res = new Response();
		res.setData(cartService.saveCart(params));
		
		return res;
	}
	
	/**
	 * 장바구니 목록
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value="/list",  method = RequestMethod.POST)
	public Response cartList(@RequestBody SOMap params) throws Exception {
		Response res = new Response();
		res.setData(cartService.selectCartList(params));
		
		return res;
	}
	
	/**
	 * 장바구니 전체삭제
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value="/delete",  method = RequestMethod.POST)
	public Response cartDelete(@RequestBody SOMap params) throws Exception {
		Response res = new Response();
		res.setData(cartService.deleteCart(params));
		
		return res;
	}
}
