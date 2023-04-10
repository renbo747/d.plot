package com.dplot.admin.controller.goods;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dplot.admin.service.goods.PromotionBannerService;
import com.dplot.admin.service.operation.MagazineBannerService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;

/**
 * @author : LKW
 * @discription : 프로모션배너 Controller
 * @fileName : PromotionBannerController.java
 * @date : 2022-04-14
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-14 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/goods/brand/banner")
public class PromotionBannerController {
	@Autowired
	private PromotionBannerService promotionBannerService;

	/**
	 * 프로모션배너 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(promotionBannerService.selectPromotionBannerList(params));
	}

	/**
	 * 프로모션배너 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody SOMap params) throws Exception {
		return new Response(promotionBannerService.updatePromotionBanner(params));
	}
	
	/**
	 * 프로모션배너 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response modify(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(promotionBannerService.modifyPromotionBanner(params, uploadFile));
	}
	
	 /**
	 * 프로모션배너 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response save(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
		 Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		 return new Response(promotionBannerService.savePromotionBanner(params, uploadFile));
	 }
	 
	 /**
	 * 프로모션배너 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response detail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(promotionBannerService.selectPromotionBannerDetail(params));
	 }
	 
	 /**
	 * 프로모션배너 변경 체크
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Response check(@RequestBody SOMap params) throws Exception {
		return new Response(promotionBannerService.checkPromotionBanner(params));
	}
}
