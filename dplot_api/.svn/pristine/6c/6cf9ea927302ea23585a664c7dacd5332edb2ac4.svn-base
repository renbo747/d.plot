package com.dplot.admin.controller.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.operation.OperationBannedService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;

/**
 * @author : LKW
 * @discription : 금칙어 Controller
 * @fileName : OperationBannedController.java
 * @date : 2022-04-12
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-12 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/setting/banned")
public class OperationBannedController {
	@Autowired
	private OperationBannedService operationBannedService;

	/**
	 * 금칙어 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(operationBannedService.selectBannedList(params));
	}

	
	 /**
	 * 금칙어 삭제
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/remove", method = RequestMethod.POST)
	 public Response remove(@RequestBody SOMap params) throws Exception{
		 return new Response(operationBannedService.removeBanned(params));
	 }
	 
	 /**
	  * 금칙어 추가
	  * @param params
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response save(@RequestBody SOMap params) throws Exception{
		 return new Response(operationBannedService.saveBanned(params));
	 }
}
