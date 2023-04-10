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

import com.dplot.admin.service.operation.MagazineKeywordService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 매거진키워드 Controller
 * @fileName : MagazineKeywordController.java
 * @date : 2022-03-31
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-03-31 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value = "/admin/operation/magazine/keyword")
public class MagazineKeywordController extends ExcelDownController {
	@Autowired
	private MagazineKeywordService magazineKeywordService;

	/**
	 * 매거진키워드 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(magazineKeywordService.selectMzKeywordList(params));
	}

	/**
	 * 매거진키워드 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<ByteArrayResource> excel(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = magazineKeywordService.selectMzKeywordExcelList(params);

		String[] header = { "No", "제목", "키워드", "유형", "등급", "노출채널", "노출상태", "현재 전시여부", "등록자", "시작일자","종료일자", "등록일자" };
		int[] columnSize = { 7 * 256, 20 * 256, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256, 10*256, 12 * 256 , 12 * 256 , 12 * 256 };
		String[] columns = { "no", "subject","wordcnt","muappchtype","mumembertype","mumemlvtype","isdisplay", "nowdisplay", "regusername", "kwsttime", "kwedtime", "regdate" };

		return makeExcelFile(header, columns, columnSize, list, "키워드영역관리 목록.xlsx");
	}

	 /**
	 * 매거진키워드 사용여부, 전시여부 수정
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/update", method = RequestMethod.POST)
	 public Response update(@RequestBody SOMap params) throws Exception{
		 return new Response(magazineKeywordService.updateMzKeyword(params));
	 }
	 
	 /**
	 * 매거진키워드 저장
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Response save(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(magazineKeywordService.saveMzKeyword(params, uploadFile));
	}
	 
	 /**
	 * 매거진키워드 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response modify(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(magazineKeywordService.modifyMzKeyword(params, uploadFile));
	}
	
	 /**
	 * 매거진키워드 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response detail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(magazineKeywordService.selectMzKeywordDetail(params));
	 }
	 
	 /**
	 * 매거진키워드 수량 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/check", method = RequestMethod.POST)
	 public Response check(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(magazineKeywordService.selectMzKeywordCheck(params));
	 }
}
