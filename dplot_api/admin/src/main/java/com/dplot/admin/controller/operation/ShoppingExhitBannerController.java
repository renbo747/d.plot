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

import com.dplot.admin.service.operation.ShoppingExhitBannerService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;

/**
 * @author : LKW
 * @discription : 전시영역배너 Controller
 * @fileName : ShoppingExhitBannerController.java
 * @date : 2022-04-05
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-05 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/shopping/exhitbanner")
public class ShoppingExhitBannerController {
	@Autowired
	private ShoppingExhitBannerService shoppingExhitBannerService;

	/**
	 * 전시영역배너 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response bannerPage(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingExhitBannerService.selectExhitBannerList(params));
	}

	/**
	 * 전시영역배너 사용여부, 전시여부 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response bannerUpdateUse(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingExhitBannerService.updateExhitBanner(params));
	}
	
	/**
	 * 전시영역배너 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response bannerModify(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(shoppingExhitBannerService.modifyExhitBanner(params, uploadFile));
	}
	
	 /**
	 * 전시영역배너 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response saveBanner(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
		 Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		 return new Response(shoppingExhitBannerService.saveExhitBanner(params, uploadFile));
	 }
	 
	 /**
	 * 전시영역배너 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response bannerDetail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(shoppingExhitBannerService.selectExhitBannerDetail(params));
	 }
	 
	 /**
	 * 전시영역배너 변경 체크
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Response bannerCheck(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingExhitBannerService.checkExhitBanner(params));
	}
}
