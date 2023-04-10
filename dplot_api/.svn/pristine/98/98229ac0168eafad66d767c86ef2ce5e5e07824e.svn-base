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

import com.dplot.admin.service.operation.MagazineBannerService;
import com.dplot.admin.service.operation.MagazineDisplayService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 전시영역 Controller
 * @fileName : MagazineDisplayController.java
 * @date : 2022-03-29
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-03-29 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/magazine/display")
public class MagazineDisplayController extends ExcelDownController {
	@Autowired
	private MagazineDisplayService magazineDisplayService;

	/**
	 * 전시영역 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response displayPage(@RequestBody SOMap params) throws Exception {
		return new Response(magazineDisplayService.selectDisplayList(params));
	}

	/**
	 * 전시영역 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response displayUpdateUse(@RequestBody SOMap params) throws Exception {
		return new Response(magazineDisplayService.updateDisplay(params));
	}
	
	/**
	 * 전시영역 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<ByteArrayResource> exhibitExcel(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = magazineDisplayService.selectDisplayExcelList(params);

		String[] header = { "노출순서", "카피", "컨텐츠 키워드", "유형", "등급", "노출채널", "노출상태", "현재 전시여부", "등록자", "시작일자", "종료일자", "등록일자" };
		int[] columnSize = { 10 * 256, 30 * 256, 15 * 256, 10 * 256, 10 * 256, 10 * 256, 10 * 256, 10 * 256, 10 * 256, 12 * 256, 12 * 256, 12 * 256 };
		String[] columns = { "sortno", "copy", "wordcnt", "mumembertype", "mumemlvtype", "muappchtype",
				"isdisplay", "nowdisplay", "regusername", "exsttime", "exedtime", "regdate" };

		return makeExcelFile(header, columns, columnSize, list, "전시영역관리 목록.xlsx");
	}
	
	/**
	 * 전시영역 사용여부, 전시여부 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response displayModify(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(magazineDisplayService.modifyDisplay(params, uploadFile));
	}
	
	 /**
	 * 전시영역 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response saveDisplay(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
		 Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		 return new Response(magazineDisplayService.saveDisplay(params, uploadFile));
	 }
	 
	 /**
	 * 전시영역 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response displayDetail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(magazineDisplayService.selectDisplayDetail(params));
	 }
	 
	 /**
	 * 전시영역 변경 체크
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Response displayCheck(@RequestBody SOMap params) throws Exception {
		return new Response(magazineDisplayService.checkDisplay(params));
	}
}
