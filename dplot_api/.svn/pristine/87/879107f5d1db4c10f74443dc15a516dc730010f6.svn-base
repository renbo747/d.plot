package com.dplot.admin.controller.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.configuration.AdminGoodsModifyAuthService;
import com.dplot.admin.service.configuration.AdminTermsManageService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;

/**
 * @author : ywm2004
 * @discription : 상품수정권한설정 Controller
 * @fileName : AdminGoodsModifyAuthController.java
 * @date : 2022-01-13
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-13	LKW		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/configuration/manage/goodsauth")
public class AdminGoodsModifyAuthController{
	@Autowired
	private AdminGoodsModifyAuthService adminGoodsModifyAuthService;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(adminGoodsModifyAuthService.selectAuthType(params));
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Response save(@RequestBody SOMap params) throws Exception {
		return new Response(adminGoodsModifyAuthService.updateAuthType(params));
	}
}
