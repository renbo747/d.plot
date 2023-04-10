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

import com.dplot.admin.service.operation.OperationNoticeService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 공지사항 Controller
 * @fileName : OperationNoticeController.java
 * @date : 2022-02-28
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-02-28 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value = {"/admin/operation/notice", "/partners/operation/notice"})
public class OperationNoticeController extends ExcelDownController {
	@Autowired
	private OperationNoticeService operationNoticeService;

	/**
	 * 공지사항 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response noticePage(@RequestBody SOMap params) throws Exception {
		return new Response(operationNoticeService.selectNoticeList(params));
	}

	/**
	 * 공지사항 사용여부 수정
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response noticeUpdateUse(@RequestBody SOMap params) throws Exception {
		return new Response(operationNoticeService.updateNotice(params));
	}

	/**
	 * 공지사항 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<ByteArrayResource> exhibitExcel(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = operationNoticeService.selectNoticeExcelList(params);

		String[] header = { "No", "상단고정", "제목", "유형", "예약여부", "노출채널", "노출상태", "작성자", "조회수", "게시상태", "게시시작일자", "고정종료일자",
				"등록일자" };
		int[] columnSize = { 7 * 256, 10 * 256, 25 * 256, 10 * 256, 10 * 256, 10 * 256, 10 * 256, 15 * 256, 10 * 256,
				10 * 256, 12 * 256, 12 * 256, 12 * 256 };
		String[] columns = { "no", "istopfix", "subject", "mumembertype", "isrightnow", "muappchtype",
				"isdisplay", "writer", "hits", "postst", "poststtime", "topedtime", "regdate" };

		return makeExcelFile(header, columns, columnSize, list, "운영관리 공지사항 목록.xlsx");
	}

	 /**
	 * 공지사항 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response saveNotice(@RequestBody SOMap params) throws Exception{
		 return new Response(operationNoticeService.saveNotice(params));
	 }
	 
	 /**
	 * 공지사항 삭제
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/delete", method = RequestMethod.POST)
	 public Response deleteNotice(@RequestBody SOMap params) throws Exception{
		 return new Response(operationNoticeService.deleteNotice(params));
	 }
	
	 /**
	 * 공지사항 수정
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/modify", method = RequestMethod.POST)
	 public Response updateNotice(@RequestBody SOMap params) throws Exception{
	 	return new Response(operationNoticeService.modifyNotice(params));
	 }
	
	 /**
	 * 공지사항 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/detail", method = RequestMethod.POST)
	 public Response noticeDetail(@RequestBody SOMap params) throws Exception
	 {
		 return new Response(operationNoticeService.selectNoticeDetail(params));
	 }
	 
	 /**
	 * 공지사항 변경 체크
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Response noticeCheck(@RequestBody SOMap params) throws Exception {
		return new Response(operationNoticeService.checkNotice(params));
	}
}
