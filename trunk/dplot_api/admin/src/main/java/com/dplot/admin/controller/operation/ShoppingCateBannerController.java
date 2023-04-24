package com.dplot.admin.controller.operation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dplot.admin.service.operation.ShoppingCateBannerService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 쇼핑카테고리배너 Controller
 * @fileName : ShoppingCateBannerController.java
 * @date : 2022-04-04
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-04 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/shopping/catebanner")
public class ShoppingCateBannerController extends ExcelDownController {
	@Autowired
	private ShoppingCateBannerService shoppingCateBannerService;

	/**
	 * 카테고리배너 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response bannerPage(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingCateBannerService.selectCateBannerList(params));
	}

	/**
	 * 카테고리배너 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response bannerUpdateUse(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingCateBannerService.updateCateBanner(params));
	}
	
	/**
	 * 카테고리배너 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response bannerModify(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(shoppingCateBannerService.modifyCateBanner(params, uploadFile));
	}
	
	 /**
	 * 카테고리배너 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response saveBanner(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
		 Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		 return new Response(shoppingCateBannerService.saveCateBanner(params, uploadFile));
	 }
	 
	 /**
	 * 카테고리배너 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response bannerDetail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(shoppingCateBannerService.selectCateBannerDetail(params));
	 }
	 
	 /**
	 * 카테고리배너 변경 체크
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Response bannerCheck(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingCateBannerService.checkCateBanner(params));
	}
	
	/**
	 * 1depth 카테고리 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public Response category(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingCateBannerService.selectCategoryList(params));
	}
}
