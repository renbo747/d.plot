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

import com.dplot.admin.service.configuration.AdminNotifyManageService;
import com.dplot.admin.service.configuration.AdminOperatorService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : ywm2004
 * @discription : 상품정보고시관리 Controller
 * @fileName : AdminNotifyTplController.java
 * @date : 2022-01-11
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-11	LKW		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/configuration/manage/notify")
public class AdminNotifyManageController extends ExcelDownController{
	@Autowired
	private AdminNotifyManageService adminNotifyManageService;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(adminNotifyManageService.selectNotifyList(params));
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Response detail(@RequestBody SOMap params) throws Exception {
		return new Response(adminNotifyManageService.selectNotifyTpl(params));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody SOMap params) throws Exception {
		return new Response(adminNotifyManageService.updateNotify(params));
	}
	
	@RequestMapping(value="/excel", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> excelDownload(@RequestBody SOMap params) throws Exception {
   	
    	List<Map<String, Object>> list = adminNotifyManageService.selectNotifyExcelList(params); 

    	// 헤더 이름
    	String[] header = {"No","상품정보고시명","사용여부","등록자","등록일자"};
    	String[] column = {"no","title","istrash","regusername","regdate"};
    	// 컬럼 너비 지정
    	int[] columnSize = {10*256, 40*256, 15*256, 20*256, 25*256};
    	
    	return makeExcelFile(header, column, columnSize, list, "상품정보고시관리목록.xlsx");
    }
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Response save(@RequestBody SOMap params) throws Exception {
		return new Response(adminNotifyManageService.saveNotifyTpl(params));
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response modify(@RequestBody SOMap params) throws Exception {
		return new Response(adminNotifyManageService.modifyNotifyTpl(params));
	}
}
