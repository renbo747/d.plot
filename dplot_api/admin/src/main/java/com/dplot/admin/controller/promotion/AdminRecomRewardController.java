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
import com.dplot.admin.service.promotion.AdminRecomRewardService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : ywm2004
 * @discription : 추천리워드 Controller
 * @fileName : AdminRecomRewardController.java
 * @date : 2021-12-22
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-22	LCK		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/promotion/promotion/reward")
public class AdminRecomRewardController extends ExcelDownController{
	@Autowired
	private AdminRecomRewardService adminRecomRewardService;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(adminRecomRewardService.selectRecomReward(params));
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Response save(@RequestBody SOMap params) throws Exception {
		return new Response(adminRecomRewardService.saveRecomReward(params));
	}
	
	@RequestMapping(value = "/log/search", method = RequestMethod.POST)
	public Response searchLog(@RequestBody SOMap params) throws Exception {
		return new Response(adminRecomRewardService.selectRecomRewardLogList(params));
	}
	
	@RequestMapping(value="/log/excel", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> excelDownload(@RequestBody SOMap params) throws Exception {
   	
    	List<Map<String, Object>> list = adminRecomRewardService.selectRecomRewardExcelList(params); 

    	// 헤더 이름
    	String[] header = {"No","혜택구분","진행기간","피추천인지급","피추천인 회원가입지급","피추천인이 첫 구매확정시 지급","소멸일자(D포인트)","중복사용여부(D포인트)","피추천인수제한","등록자","저장일자"};
    	String[] column = {"no","recomtype","recomday","revpoint","recjoinpoint","reccfmpoint","limitdate","isepointdup","reclimitcnt","regusername","regdate"};
    	// 컬럼 너비 지정
    	int[] columnSize = {8*256, 15*256, 35*256, 25*256, 25*256, 25*256, 20*256, 20*256, 15*256, 15*256, 25*256};
    	
    	return makeExcelFile(header, column, columnSize, list, "추천리워드이력.xlsx");
    }
}
