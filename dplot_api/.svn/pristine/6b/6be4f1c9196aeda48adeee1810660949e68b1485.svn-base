package com.dplot.front.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.front.service.FrontCategoryService;
import com.dplot.util.Util;
/**
 * 
 * @FileName : CategoryController.java
 * @Project : datapick_api
 * @Date : 2021. 11. 12. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 11. 12.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@RestController
@RequestMapping("category")
public class CategoryController {
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private FrontCategoryService frontCategoryService;
	
	/**
	 * 카테고리 메인페이지 정보 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public Response categoryIndex(@RequestBody SOMap param) throws Exception {
		return new Response(frontCategoryService.selectCategoryList(param));
	}
	
	/**
	 * 해당 카테고리 idx의 정보와 해당 idx 하위 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST)
	public Response selectCategoryInfo(@RequestBody SOMap param) throws Exception {
		return new Response(frontCategoryService.selectCategoryInfo(param));
	}
	
	/**
	 * 카테고리 메인페이지 정보 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/pcMain", method=RequestMethod.POST)
	public Response pcMain(@RequestBody SOMap param) throws Exception {
		return new Response(frontCategoryService.selectPcCategoryList(param));
	}
}
