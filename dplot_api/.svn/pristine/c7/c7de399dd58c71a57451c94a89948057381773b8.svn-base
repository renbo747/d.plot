package com.dplot.admin.controller.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.operation.OperationCardBenefitService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;

/**
 * @author : LKW
 * @discription : 카드혜택 Controller
 * @fileName : OperationCardBenefitController.java
 * @date : 2022-04-19
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-19 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/setting/cardbenefit")
public class OperationCardBenefitController {
	@Autowired
	private OperationCardBenefitService operationCardBenefitService;

	/**
	 * 카드혜택 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(operationCardBenefitService.selectCardBenefit(params));
	}
	
	 /**
	 * 카드혜택 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response save(@RequestBody SOMap params) throws Exception{
		 return new Response(operationCardBenefitService.saveCardBenefit(params));
	 }
}
