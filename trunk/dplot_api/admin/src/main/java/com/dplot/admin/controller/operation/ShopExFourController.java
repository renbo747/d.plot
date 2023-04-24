package com.dplot.admin.controller.operation;

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

import com.dplot.admin.service.operation.ShopExFourService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 전시영역4 Controller
 * @fileName : ShopExFourController.java
 * @date : 2022-04-06
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-06 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value = "/admin/operation/shopping/shopexfour")
public class ShopExFourController extends ExcelDownController {
	@Autowired
	private ShopExFourService shopExFourService;

	/**
	 * 전시영역4 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(shopExFourService.selectShopExFourList(params));
	}

	/**
	 * 전시영역4 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<ByteArrayResource> excel(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = shopExFourService.selectShopExFourExcelList(params);

		String[] header = { "No", "제목", "상품", "유형", "등급", "노출채널", "노출상태", "현재 전시여부", "등록자", "시작일자","종료일자", "등록일자" };
		int[] columnSize = { 7 * 256, 20 * 256, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256, 12 * 256 , 12 * 256 , 12 * 256 };
		String[] columns = { "no", "subject","goodscnt","muappchtype","mumembertype","mumemlvtype","isdisplay", "nowdisplay", "regusername", "exsttime", "exedtime", "regdate" };

		return makeExcelFile(header, columns, columnSize, list, "전시영역4관리 목록.xlsx");
	}

	 /**
	 * 전시영역4 사용여부, 전시여부 수정
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/update", method = RequestMethod.POST)
	 public Response update(@RequestBody SOMap params) throws Exception{
		 return new Response(shopExFourService.updateShopExFour(params));
	 }
	 
	 /**
	 * 전시영역4 저장
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Response save(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(shopExFourService.saveShopExFour(params, uploadFile));
	}
	 
	 /**
	 * 전시영역4 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response modify(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(shopExFourService.modifyShopExFour(params, uploadFile));
	}
	
	 /**
	 * 전시영역4 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response detail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(shopExFourService.selectShopExFourDetail(params));
	 }
	 
	 /**
	 * 전시영역4 수량 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/check", method = RequestMethod.POST)
	 public Response check(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(shopExFourService.selectShopExFourCheck(params));
	 }
}
