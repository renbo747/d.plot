package com.dplot.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.front.service.BrandService;

@RestController
@RequestMapping("/brand")
public class BrandController {
	@Autowired
	private  BrandService brandService;
	
	/**
	 * 브랜드 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list" , method = RequestMethod.POST)
	public Response selectBrandList(@RequestBody SOMap param) throws Exception {
		return new Response(brandService.selectBrandList(param));
	}
	
	/**
	 * 매거진 브랜드 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mzlist" , method = RequestMethod.POST)
	public Response selectMzBrandList(@RequestBody SOMap param) throws Exception {
		return new Response(brandService.selectMzBrandList(param));
	}
	
	/**
	 * 매거진 브랜드 상세 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mzdetail" , method = RequestMethod.POST)
	public Response mzBrandDetail(@RequestBody SOMap param) throws Exception {
		return new Response(brandService.selectMzBrandDetail(param));
	}
	
	/**
	 * 매거진 브랜드 좋아요 변경
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wish" , method = RequestMethod.POST)
	public Response wish(@RequestBody SOMap param) throws Exception {
		return new Response(brandService.changeWish(param));
	}
	
	/**
	 * 필터 브랜드 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/filter" , method = RequestMethod.POST)
	public Response selectFilterBrandList(@RequestBody SOMap param) throws Exception {
		return new Response(brandService.selectFilterBrandList(param));
	}
}
