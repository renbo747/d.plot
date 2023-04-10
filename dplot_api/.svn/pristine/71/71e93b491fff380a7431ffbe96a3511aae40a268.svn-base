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

import com.dplot.admin.service.operation.OperSubscribeService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 구독관리 Controller
 * @fileName : OperationSubcribeController.java
 * @date : 2022-04-08
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-08 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/setting/subscribe")
public class OperationSubcribeController extends ExcelDownController {
	@Autowired
	private OperSubscribeService operSubscribeService;

	/**
	 * 구독관리 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(operSubscribeService.selectSubscribeList(params));
	}

	
	 /**
	 * 구독관리 구독취소
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/update", method = RequestMethod.POST)
	 public Response update(@RequestBody SOMap params) throws Exception{
		 return new Response(operSubscribeService.updateSubscribe(params));
	 }
	 
	 /**
	 * 구독관리 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<ByteArrayResource> excel(@RequestBody SOMap params) throws Exception {
		List<Map<String, Object>> list = operSubscribeService.selectSubscribeExcelList(params);

		String[] header = {"구독신청일시", "구독취소일시", "이메일 소유 ID", "이메일", "상태"};
		int[] columnSize = { 18 * 256, 18 * 256, 12 * 256, 15 * 256, 15 * 256, 10 * 256};
		String[] columns = { "subregdate", "cancaldate", "subuserid", "email", "iscancel" };

		return makeExcelFile(header, columns, columnSize, list, "구독관리 목록.xlsx");
	}
}
