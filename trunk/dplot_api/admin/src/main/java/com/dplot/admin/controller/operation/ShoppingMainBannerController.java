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

import com.dplot.admin.service.operation.MagazineBannerService;
import com.dplot.admin.service.operation.ShoppingMainBannerService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 쇼핑메인배너 Controller
 * @fileName : ShoppingMainBannerController.java
 * @date : 2022-04-04
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-04 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/shopping/banner")
public class ShoppingMainBannerController extends ExcelDownController {
	@Autowired
	private ShoppingMainBannerService shoppingMainBannerService;

	/**
	 * 메인배너 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response bannerPage(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingMainBannerService.selectMainBannerList(params));
	}

	/**
	 * 메인배너 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response bannerUpdateUse(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingMainBannerService.updateMainBanner(params));
	}
	
	/**
	 * 메인배너 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response bannerModify(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(shoppingMainBannerService.modifyBanner(params, uploadFile));
	}
	
	 /**
	 * 메인배너 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response saveBanner(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
		 Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		 return new Response(shoppingMainBannerService.saveMainBanner(params, uploadFile));
	 }
	 
	 /**
	 * 메인배너 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response bannerDetail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(shoppingMainBannerService.selectBannerDetail(params));
	 }
	 
	 /**
	 * 메인배너 변경 체크
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Response bannerCheck(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingMainBannerService.checkBanner(params));
	}
}
