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

import com.dplot.admin.service.operation.MagazineCategoryService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 매거진카테고리 Controller
 * @fileName : MagazineCategoryController.java
 * @date : 2022-04-01
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-01 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value = "/admin/operation/magazine/category")
public class MagazineCategoryController extends ExcelDownController {
	@Autowired
	private MagazineCategoryService magazineCategoryService;

	/**
	 * 매거진카테고리 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(magazineCategoryService.selectMzCategoryList(params));
	}

	/**
	 * 매거진카테고리 사용여부, 전시여부 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody SOMap params) throws Exception {
		return new Response(magazineCategoryService.updateMzCategory(params));
	}

	/**
	 * 매거진카테고리 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<ByteArrayResource> excel(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = magazineCategoryService.selectMzCategoryExcelList(params);

		String[] header = { "No","제목","연결된 트렌드 컨텐츠", "노출상태", "작성자", "등록일자" };
		int[] columnSize = { 7 * 256, 30 * 256, 15 * 256, 10 * 256 , 10 * 256 , 12 * 256 };
		String[] columns = { "no", "subject","contentscnt","isdisplay", "regusername", "regdate" };

		return makeExcelFile(header, columns, columnSize, list, "매거진카테고리관리 목록.xlsx");
	}

	 /**
	 * 매거진카테고리 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response save(@RequestBody SOMap params) throws Exception{
		 return new Response(magazineCategoryService.saveMzCategory(params));
	 }
	 
	 /**
	 * 매거진카테고리 수정
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/modify", method = RequestMethod.POST)
	 public Response modify(@RequestBody SOMap params) throws Exception{
	 	return new Response(magazineCategoryService.modifyMzCategory(params));
	 }
	
	 /**
	 * 매거진카테고리 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response detail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(magazineCategoryService.selectMzCategoryDetail(params));
	 }
	 
	 /**
	 * 매거진카테고리 변경 체크
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Response check(@RequestBody SOMap params) throws Exception {
		return new Response(magazineCategoryService.checkMzCategory(params));
	}
}
