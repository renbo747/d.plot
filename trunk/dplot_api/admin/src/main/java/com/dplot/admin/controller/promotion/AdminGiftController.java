package com.dplot.admin.controller.promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dplot.admin.service.promotion.AdminGiftService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : ywm2004
 * @discription : 사은품 Controller
 * @fileName : AdminGiftController.java
 * @date : 2021-12-20
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-20	LCK		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/promotion/promotion/gift")
public class AdminGiftController extends ExcelDownController{
	@Autowired
	private AdminGiftService adminGiftService;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(adminGiftService.selectGiftList(params));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody SOMap params) throws Exception {
		return new Response(adminGiftService.updateGiftUse(params));
	}
	
	@RequestMapping(value="/excel", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> excelDownload(@RequestBody SOMap params) throws Exception {
   	
    	List<Map<String, Object>> list = adminGiftService.selectGiftExcelList(params); 

    	// 헤더 이름
    	String[] header = {"사은품코드","사은품명","연결","재고","사용여부","등록일자"};
    	String[] column = {"giftcode","giftname","erpgiftcnt","giftstockcnt","istrash","regdate"};
    	// 컬럼 너비 지정
    	int[] columnSize = {15*256, 35*256, 13*256, 15*256, 13*256, 15*256};
    	
    	return makeExcelFile(header, column, columnSize, list, "사은품목록.xlsx");
    }
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Response save(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(adminGiftService.saveGift(params, uploadFile));
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Response detail(@RequestBody SOMap params) throws Exception {
		return new Response(adminGiftService.selectGiftDetail(params));
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response modify(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(adminGiftService.modifyGift(params, uploadFile));
	}
}
