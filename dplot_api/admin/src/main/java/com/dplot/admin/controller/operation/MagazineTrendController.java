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

import com.dplot.admin.service.operation.MagazineCategoryService;
import com.dplot.admin.service.operation.MagazineTrendService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 매거진트랜드 Controller
 * @fileName : MagazineTrendController.java
 * @date : 2022-04-01
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-01 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/magazine/trend")
public class MagazineTrendController extends ExcelDownController {
	@Autowired
	private MagazineTrendService magazineTrendService;

	@Autowired
	private MagazineCategoryService magazineCategoryService;
	/**
	 * 매거진트랜드 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(magazineTrendService.selectTrendList(params));
	}

	/**
	 * 매거진트랜드 사용여부, 전시여부  수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody SOMap params) throws Exception {
		return new Response(magazineTrendService.updateTrend(params));
	}
	
	/**
	 * 매거진트랜드 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<ByteArrayResource> excel(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = magazineTrendService.selectTrendExcelList(params);

		String[] header = { "노출순서", "쇼핑메인", "제목", "카테고리", "좋아요", "유형", "등급", "노출채널", "노출상태", "현재 전시여부", "등록자", "시작일자", "종료일자", "등록일자" };
		int[] columnSize = { 10 * 256, 10 * 256, 30 * 256, 15 * 256, 10 * 256,10 * 256, 10 * 256, 10 * 256, 10 * 256, 10 * 256, 10 * 256,  12 * 256, 12 * 256, 12 * 256 };
		String[] columns = { "sortno", "isdispshop", "subject", "catesubject", "likecnt", "mumembertype", "mumemlvtype", "muappchtype",
				"isdisplay", "nowdisplay", "regusername", "trsttime", "tredtime", "regdate" };

		return makeExcelFile(header, columns, columnSize, list, "트렌드컨텐츠관리 목록.xlsx");
	}
	
	/**
	 * 매거진트랜드 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Response modify(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(magazineTrendService.modifyTrend(params, uploadFile));
	}
	
	 /**
	 * 매거진트랜드 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response save(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
		 Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		 return new Response(magazineTrendService.saveTrend(params, uploadFile));
	 }
	 
	 /**
	 * 매거진트랜드 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response detail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(magazineTrendService.selectTrendDetail(params));
	 }
	 
	 /**
	 * 매거진트랜드 변경 체크
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Response check(@RequestBody SOMap params) throws Exception {
		return new Response(magazineTrendService.checkTrend(params));
	}
	
	/**
	 * 매거진트랜드 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public Response category(@RequestBody SOMap params) throws Exception {
		return new Response(magazineTrendService.selectAllMzCategoryList(params));
	}
}
