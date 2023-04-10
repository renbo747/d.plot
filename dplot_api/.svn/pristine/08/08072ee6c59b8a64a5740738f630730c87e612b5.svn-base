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

import com.dplot.admin.service.operation.OperationMainPopupService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 팝업 Controller
 * @fileName : OperationMainPopupController.java
 * @date : 2022-04-11
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-11 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/setting/mainpopup")
public class OperationMainPopupController extends ExcelDownController {
	@Autowired
	private OperationMainPopupService operationMainPopupService;

	/**
	 * 메인팝업 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(operationMainPopupService.selectMainPopupList(params));
	}

	/**
	 * 메인팝업 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody SOMap params) throws Exception {
		return new Response(operationMainPopupService.updateMainPopup(params));
	}
	
	/**
	 * 메인팝업 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response modify(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(operationMainPopupService.modifyMainPopup(params, uploadFile));
	}
	
	 /**
	 * 메인팝업 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response save(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
		 Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		 return new Response(operationMainPopupService.saveMainPopup(params, uploadFile));
	 }
	 
	 /**
	 * 메인팝업 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response detail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(operationMainPopupService.selectMainPopupDetail(params));
	 }
	 
	 /**
	 * 메인팝업 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<ByteArrayResource> excel(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = operationMainPopupService.selectMainPopupExcelList(params);

		String[] header = {"노출순서", "제목", "유형", "등급", "노출채널", "노출상태","현재 전시여부", "등록자", "시작일자", "종료일자", "등록일자"};
		int[] columnSize = { 12 * 256, 25 * 256, 15 * 256, 15 * 256, 15 * 256, 12 * 256, 12 * 256, 12 *256, 12 * 256, 12 * 256, 12 * 256};
		String[] columns = { "sortno", "subject", "mumembertype", "mumemlvtype", "muappchtype", "isdisplay", "nowdisplay", "regusername", "popsttime", "popedtime", "regdate" };

		return makeExcelFile(header, columns, columnSize, list, "메인팝업 목록.xlsx");
	}
	 
	 /**
	 * 메인팝업 변경 체크
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Response check(@RequestBody SOMap params) throws Exception {
		return new Response(operationMainPopupService.checkMainPopup(params));
	}
}
