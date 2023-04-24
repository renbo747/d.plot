package com.dplot.admin.controller.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.configuration.AdminTermsManageService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;

/**
 * @author : ywm2004
 * @discription : 약관관리 Controller
 * @fileName : AdminTermsController.java
 * @date : 2022-01-12
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-12	LKW		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/configuration/manage/terms")
public class AdminTermsManageController{
	@Autowired
	private AdminTermsManageService adminTermsManageService;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(adminTermsManageService.selectTermsList(params));
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Response detail(@RequestBody SOMap params) throws Exception {
		return new Response(adminTermsManageService.selectTerms(params));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody SOMap params) throws Exception {
		return new Response(adminTermsManageService.updateTerms(params));
	}
}
