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

import com.dplot.admin.service.configuration.AdminCodeManageService;
import com.dplot.admin.service.configuration.AdminOperatorService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : ywm2004
 * @discription : 코드관리 Controller
 * @fileName : AdminCodeManageController.java
 * @date : 2022-01-10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-10	LKW		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/configuration/manage/code")
public class AdminCodeManageController extends ExcelDownController{
	@Autowired
	private AdminCodeManageService adminCodeManageService;
	
	/**
	 * 공통코드그룹 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(adminCodeManageService.selectCodeClassList(params));
	}
	
	/**
	 * 공통코드 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search/code", method = RequestMethod.POST)
	public Response codeSearch(@RequestBody SOMap params) throws Exception {
		return new Response(adminCodeManageService.selectCodeList(params));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody SOMap params) throws Exception {
		return new Response(adminCodeManageService.updateCodeUse(params));
	}
	
	@RequestMapping(value="/excel", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> excelDownload(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = null;
		if("G".equals(params.getStr("type"))){
			list = adminCodeManageService.selectCodeClassExcelList(params); 
			String[] header = {"No","공통코드 그룹","공통코드 그룹명","설명","사용여부"};
			String[] column = {"no","cmclass","classname","detail","istrash"};
	    	// 컬럼 너비 지정
	    	int[] columnSize = {10*256, 25*256, 25*256, 35*256, 15*256};
	    	
	    	return makeExcelFile(header, column, columnSize, list, "공통코드그룹목록.xlsx");
		} else {
			list = adminCodeManageService.selectCodeExcelList(params);
	    	String[] header = {"No","공통코드","공통코드명","설명","비고","사용여부"};
			String[] column = {"no","cmcode","codename","detail","note","istrash"};
	    	// 컬럼 너비 지정
	    	int[] columnSize = {10*256, 25*256, 25*256, 25 *256, 25*256, 15*256};
	    	
	    	return makeExcelFile(header, column, columnSize, list, "공통코드목록.xlsx");
		}
    }
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Response save(@RequestBody SOMap params) throws Exception {
		return new Response(adminCodeManageService.saveCode(params));
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Response detail(@RequestBody SOMap params) throws Exception {
		return new Response(adminCodeManageService.selectCodeDetail(params));
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response modify(@RequestBody SOMap params) throws Exception {
		return new Response(adminCodeManageService.modifyCode(params));
	}
}
