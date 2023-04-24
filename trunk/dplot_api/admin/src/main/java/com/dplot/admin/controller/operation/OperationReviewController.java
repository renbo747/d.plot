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

import com.dplot.admin.service.operation.OperationReviewService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 리뷰 Controller
 * @fileName : OperationReviewController.java
 * @date : 2022-03-03
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-03-03 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value = {"/admin/operation/review", "/partners/operation/review"})
public class OperationReviewController extends ExcelDownController {
	@Autowired
	private OperationReviewService operationReviewService;

	/**
	 * 리뷰 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(operationReviewService.selectReviewList(params));
	}

	/**
	 * 리뷰 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<ByteArrayResource> reviewExcel(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = operationReviewService.selectReviewExcelList(params);
		
		if("true".equals(params.getStr("isadmin"))) {
			String[] header = { "No", "베스트", "미노출", "상품명", "리뷰내용", "아이디", "이름", "총상품평점", "좋아요", "포토&동영상", "신고","시작일자","종료일자", "등록일자" };
			int[] columnSize = { 7 * 256, 8 * 256, 10 * 256, 15 * 256, 30 * 256, 10 * 256, 15 * 256, 10 * 256, 10 * 256, 12 * 256, 7 * 256, 12 * 256 , 12 * 256 , 12 * 256 };
			String[] columns = { "no", "isbest", "isdisplay", "goodsname", "content", "reguserid", "regusername", "totpoint", "goodcnt", "filecnt", "noticnt", "beststtime", "bestedtime", "regdate" };
			return makeExcelFile(header, columns, columnSize, list, "리뷰 목록.xlsx");
		} else {
			String[] header = { "No", "상품명", "리뷰내용", "총상품평점", "좋아요", "포토&동영상", "등록일자" };
			int[] columnSize = { 7 * 256, 15 * 256, 30 * 256, 10 * 256, 10 * 256, 12 * 256, 12 * 256 };
			String[] columns = { "no", "goodsname", "content", "totpoint", "goodcnt", "filecnt", "regdate" };						
			return makeExcelFile(header, columns, columnSize, list, "리뷰 목록.xlsx");
		}

	}

	 /**
	 * 리뷰 수정
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/modify", method = RequestMethod.POST)
	 public Response modifyReview(@RequestBody SOMap params) throws Exception{
		 return new Response(operationReviewService.modifyReview(params));
	 }
	
//	 /**
//	 * 공지사항 수정
//	 * @param params
//	 * @return Response
//	 * @throws Exception
//	 */
//	 @RequestMapping(value = "/modify", method = RequestMethod.POST)
//	 public Response updateNotice(@RequestBody SOMap params) throws Exception{
//	 	return new Response(operationNoticeService.modifyNotice(params));
//	 }
	
	 /**
	 * 리뷰 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response reviewDetail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(operationReviewService.selectReviewDetail(params));
	 }
	 
	 /**
	 * 베스트 리뷰 수량 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/check", method = RequestMethod.POST)
	 public Response reviewCheck(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(operationReviewService.selectReviewCheck(params));
	 }
	 
	 
	 /**
	 * 리뷰신고 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/notilist", method = RequestMethod.POST)
	public Response notilist(@RequestBody SOMap params) throws Exception {
		return new Response(operationReviewService.selectReviewNotiList(params));
	}
}
