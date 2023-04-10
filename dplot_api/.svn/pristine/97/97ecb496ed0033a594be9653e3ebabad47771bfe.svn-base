package com.dplot.admin.controller.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.operation.OperationSearchLinkService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;

/**
 * @author : LKW
 * @discription : 검색창링크 Controller
 * @fileName : OperationSearchLinkController.java
 * @date : 2022-04-12
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-12 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/setting/searchlink")
public class OperationSearchLinkController {
	@Autowired
	private OperationSearchLinkService operationSearchLinkService;

	/**
	 * 검색창링크 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(operationSearchLinkService.selectSearchLink(params));
	}
	 
	 /**
	  * 검색창링크 저장
	  * @param params
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response save(@RequestBody SOMap params) throws Exception{
		 return new Response(operationSearchLinkService.saveSearchLink(params));
	 }
}
