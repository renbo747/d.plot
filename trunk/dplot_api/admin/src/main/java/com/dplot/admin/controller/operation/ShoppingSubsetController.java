package com.dplot.admin.controller.operation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.operation.ShoppingSubsetService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 배너대용문구 Controller
 * @fileName : ShoppingSubsetController.java
 * @date : 2022-04-04
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-04 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/shopping/subset")
public class ShoppingSubsetController extends ExcelDownController {
	@Autowired
	private ShoppingSubsetService shoppingSubsetService;

	/**
	 * 배너대용문구 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingSubsetService.selectSubsetList(params));
	}

	/**
	 * 배너대용문구 사용여부, 전시여부  수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingSubsetService.updateSubset(params));
	}
	
	/**
	 * 배너대용문구 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<ByteArrayResource> excel(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = shoppingSubsetService.selectSubsetExcelList(params);

		String[] header = { "No", "문구(PC)", "문구(모바일)", "유형", "등급", "노출채널", "노출상태", "등록자", "등록일자" };
		int[] columnSize = { 10 * 256, 30 * 256, 30 * 256, 10 * 256, 10 * 256, 10 * 256, 10 * 256, 10 * 256, 12 * 256 };
		String[] columns = { "no", "content", "mobilecontent", "mumembertype", "mumemlvtype", "muappchtype",
				"isdisplay", "regusername", "regdate" };

		return makeExcelFile(header, columns, columnSize, list, "배너대용문구 목록.xlsx");
	}
	
	/**
	 * 배너대용문구 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response modify(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingSubsetService.modifySubset(params));
	}
	
	 /**
	 * 배너대용문구 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response save(@RequestBody SOMap params) throws Exception{
		 return new Response(shoppingSubsetService.saveSubset(params));
	 }
	 
	 /**
	 * 배너대용문구 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response detail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(shoppingSubsetService.selectSubsetDetail(params));
	 }
	 
	 /**
	 * 배너대용문구 변경 체크
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Response check(@RequestBody SOMap params) throws Exception {
		return new Response(shoppingSubsetService.checkSubset(params));
	}
}
