package com.dplot.admin.controller.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.configuration.AdminOperatorService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : ywm2004
 * @discription : 운영자관리 Controller
 * @fileName : AdminOperatorController.java
 * @date : 2021-12-20
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-20	LKW		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/configuration/operator/operator")
public class AdminOperatorController extends ExcelDownController{
	@Autowired
	private AdminOperatorService adminOperatorService;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(adminOperatorService.selectOperatorList(params));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody SOMap params) throws Exception {
		return new Response(adminOperatorService.updateOperatorUse(params));
	}
	
	@RequestMapping(value="/excel", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> excelDownload(@RequestBody SOMap params) throws Exception {
   	
    	List<Map<String, Object>> list = adminOperatorService.selectOperatorExcelList(params); 

    	// 헤더 이름
    	String[] header = {"No","아이디","이름","휴대폰전화번호","접속수","최근접속일시","사용여부","등록자","등록일자"};
    	String[] column = {"no","userid","name","mobile","visitcnt","visitdate","istrash","regusername","regdate"};
    	// 컬럼 너비 지정
    	int[] columnSize = {10*256, 20*256, 20*256, 25*256, 15*256, 20*256, 15*256, 20*256, 25*256};
    	
    	return makeExcelFile(header, column, columnSize, list, "운영자목록.xlsx");
    }
	
	@RequestMapping(value="/idcheck", method = RequestMethod.POST)
	public Response idCheck(@RequestBody SOMap params) throws Exception {
		return new Response(adminOperatorService.checkIdDupl(params));
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Response detail(@RequestBody SOMap params) throws Exception {
		return new Response(adminOperatorService.selectOperator(params));
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response modify(@RequestBody SOMap params) throws Exception {
		return new Response(adminOperatorService.modifyOperator(params));
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Response save(@RequestBody SOMap params) throws Exception {
		return new Response(adminOperatorService.insertOperator(params));
	}
}
