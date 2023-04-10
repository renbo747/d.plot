package com.dplot.front.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dplot.annontation.FrontInfo;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.CommonOrderService;
import com.dplot.front.service.NoneMemberService;

@RestController
@RequestMapping("/nonemember")
public class NoneMemberController {

	@Autowired
	private NoneMemberService noneMemberService;
	
	@Autowired
	private CommonOrderService commonOrderService;
	
	@Autowired
	private CJMessageService cjMessageService;
	
	/**********************
	 * 대쉬보드 영역 조회 => 사용중
	 ********************/
	@FrontInfo
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public Response selectMypageInfo(@RequestBody SOMap param) throws Exception {
		return noneMemberService.selectNoneMemberInfo(param);
	}
	
	/**
	 * as 신청
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/apply", method = RequestMethod.POST)
	public Response applyAs(@RequestBody SOMap param) throws Exception {
		return new Response(noneMemberService.applyAs(param));
	}
	
	/**
	 * as 신청 완료
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/applysave", method = RequestMethod.POST)
	public Response applySave(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(noneMemberService.applySave(params, uploadFile));
	}
	
	/**
	 * as 신청목록 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/applylist", method = RequestMethod.POST)
	public Response applyList(@RequestBody SOMap param) throws Exception {
		SOMap result = noneMemberService.applyList(param);
		result.put("myorder", noneMemberService.applyMyOrder(param));		
		return new Response(result);
	}
	
	/**
	 * as 신청목록 상세
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/applydetail", method = RequestMethod.POST)
	public Response applyDetail(@RequestBody SOMap param) throws Exception {
		return new Response(noneMemberService.applyDetail(param));
	}
	
	/**
	 * as 신청목록 상세
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/applydelete", method = RequestMethod.POST)
	public Response applyDelete(@RequestBody SOMap param) throws Exception {
		return new Response(noneMemberService.applyDelete(param));
	}	
	
	/**
	 * as 신청 완료 시 알림톡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/sendapply", method = RequestMethod.POST)
	public Response sendApply(@RequestBody SOMap param) throws Exception {
		SOMap result = new SOMap();
		cjMessageService.sendAsApply(param);

		return new Response(result);
	}
	
	/**
	 * as 신청 완료
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/applyrevise", method = RequestMethod.POST)
	public Response applyRevise(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(noneMemberService.applyRevise(params, uploadFile));
	}
	
	/**
	 * as 신청 완료
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/applymyorder", method = RequestMethod.POST)
	public Response applyMyOrder(@RequestBody SOMap params) throws Exception {
		SOMap result = new SOMap();
		result.put("list", noneMemberService.applyMyOrder(params));
		return new Response(result);
	}
	
	/**********************
	 * 주문취소(입금대기)
	 ********************/
	@RequestMapping(value = "/order/cancel", method = RequestMethod.POST)
	public Response orderCancel(@RequestBody SOMap param) throws Exception {
		SOMap result = commonOrderService.confirmCancel(param);
		return new Response(result); 
	}
	
	/**********************
	 * 주문/배송 구매확정처리
	 ********************/
	@RequestMapping(value = "/order/confirm", method = RequestMethod.POST)
	public Response confirmOrder(@RequestBody SOMap param) throws Exception {
		param.put("ordcfmtype", "OCT001");
		return new Response(commonOrderService.confirmOrder(param));
	}
	
	/**********************
	 * 배송완료(수취확인)
	 ********************/
	@RequestMapping(value = "/order/delivcomplete", method = RequestMethod.POST)
	public Response delivComplete(@RequestBody SOMap param) throws Exception {
		
		SOMap result = commonOrderService.delivComplete(param);
		
		return new Response(result); 
	}
	
	/**
	 * 주문/배송 내역 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/list", method = RequestMethod.POST)
	public Response selectOrderList(@RequestBody SOMap param) throws Exception {
		SOMap result = noneMemberService.selectOrderList(param);
		return new Response(result);
	}
	
	/**
	 * 주문/배송 내역 상세 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/detail", method = RequestMethod.POST)
	public Response selectOrderDetail(@RequestBody SOMap param) throws Exception {
		SOMap result = noneMemberService.selectOrderDetail(param);
		return new Response(result);
	}
	
	/**
	 * 주문/배송 내역 상세 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/claim/list", method = RequestMethod.POST)
	public Response selectClaimList(@RequestBody SOMap param) throws Exception {
		SOMap result =  noneMemberService.selectClaimList(param);
		return new Response(result);
	}
	
	/**
	 * 클레임 신청
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	@RequestMapping(value="/claim/apply", method=RequestMethod.POST)
	public Response claimApply(@RequestBody SOMap params) throws Exception{
		SOMap result = noneMemberService.selectClaimApply(params);
		return new Response(result);
	}
	
	/**
	 * 클레임 신청 재계산
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	@RequestMapping(value="/claim/cal", method=RequestMethod.POST)
	public Response calCalim(@RequestBody SOMap params) throws Exception{
		SOMap result = noneMemberService.calClaimApply(params);
		return new Response(result);
	}
	
	/**
	 * 클레임 상세
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	@RequestMapping(value="/claim/detail", method=RequestMethod.POST)
	public Response detailCalim(@RequestBody SOMap params) throws Exception{
		SOMap result = noneMemberService.detailClaim(params);
		return new Response(result);
	}
	
	/**
	 * 클레임 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	@RequestMapping(value="/claim/save", method=RequestMethod.POST)
	public Response applyCalim(MultipartHttpServletRequest mReq, @RequestPart("params") SOMap params) throws Exception{
		SOMap result = noneMemberService.saveClaimApply(mReq.getFileMap(), params);
		return new Response(result);
	}
	
	/**
	 * 클레임 결제저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	@RequestMapping(value="/claim/payment", method=RequestMethod.POST)
	public Response paymentCalim(@RequestBody SOMap params) throws Exception{
		params.put("amount", params.getDbInt("amount"));
		params.put("paymentkey", params.getDbStr("paymentkey"));
		params.put("clmno", params.getDbStr("orderid"));
		
		SOMap result = noneMemberService.saveClaimPayment(params);
		
		return new Response(result);
	}
	
	/**
	 * 클레임 취소
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	@RequestMapping(value="/claim/cancel", method=RequestMethod.POST)
	public Response cancelCalim(@RequestBody SOMap params) throws Exception{
		SOMap result = noneMemberService.cancelClaim(params);
		
		return new Response(result);
	}
	
	/**
	 * 배송지 변경
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	@RequestMapping(value="/order/rcvsave", method=RequestMethod.POST)
	public Response applyCalim(@RequestBody SOMap params) throws Exception{
		SOMap result = noneMemberService.updateOrderRcvInfo(params);
		return new Response(result);
	}
	
	/**
	 * 클레임 정보
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	@RequestMapping(value="/claim/info", method=RequestMethod.POST)
	public Response claimInfo(@RequestBody SOMap params) throws Exception{
		SOMap result = noneMemberService.selectClaimInfo(params);
		
		return new Response(result);
	}
	
}
