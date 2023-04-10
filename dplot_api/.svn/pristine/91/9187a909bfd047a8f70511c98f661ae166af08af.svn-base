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
import com.dplot.front.service.MypageService;

/**
 * 
 * @FileName : MypageController.java
 * @Project : datapick_api
 * @Date : 2021. 12. 30. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 12. 30.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@RestController
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	private MypageService mypageService;
	
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
		return mypageService.selectMypageInfo(param);
	}
	
	/**********************
	 * 회원정보 조회 => 사용중
	 ********************/
	@RequestMapping(value = "/grade",method = RequestMethod.POST)
	public Response selectMyGradeInfo(@RequestBody SOMap param) throws Exception {
		return new Response(mypageService.selectMyGradeInfo(param));
	}
	
	/**********************
	 * 회원정보 조회 => 사용중
	 ********************/
	@RequestMapping(value = "/payInfo",method = RequestMethod.POST)
	public Response selectMyPayInfo(@RequestBody SOMap param) throws Exception {
		return new Response(mypageService.selectMyPayInfo(param));
	}
	
	/**********************
	 * 메시지 목록 조회
	 ********************/
	@RequestMapping(value = "/msgList",method = RequestMethod.POST)
	public Response selectMyMsgList(@RequestBody SOMap param) throws Exception {
		return new Response(mypageService.selectMyMsgList(param));
	}
	/**********************
	 * 메시지 목록 삭제
	 ********************/
	@RequestMapping(value = "/msgList/del",method = RequestMethod.POST)
	public Response deleteMyMsg(@RequestBody SOMap param) throws Exception {
		return new Response(mypageService.deleteMyMsg(param));
	}
	
	/**********************
	 * 리워드 지급 내역 조회
	 ********************/
	@RequestMapping(value = "/rewardpay/list",method = RequestMethod.POST)
	public Response rewardpayList(@RequestBody SOMap param) throws Exception {
		return new Response(mypageService.rewardpayList(param));
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
	 * 구매확정 적립금조회
	 ********************/
	@RequestMapping(value = "/order/reserve", method = RequestMethod.POST)
	public Response selectReserve(@RequestBody SOMap param) throws Exception {
		
		SOMap result = commonOrderService.selectConfirmReserve(param);
		
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
	
	/**********************
	 * 적립금 내역 목록 조회
	 ********************/
	@RequestMapping(value = "/reserves/list", method = RequestMethod.POST)
	public Response selectReserveList(@RequestBody SOMap param) throws Exception {
		return mypageService.selectReserveList(param);
	}
	
	/**********************
	 * e-포인트 내역 목록 조회
	 ********************/
	@RequestMapping(value = "/epoint/list", method = RequestMethod.POST)
	public Response selectEpointList(@RequestBody SOMap param) throws Exception {
		return mypageService.selectEpointList(param);
	}
	
	/**********************
	 * 친구초대 리워드 보상정보 조회
	 ********************/
	@RequestMapping(value = "/reward", method = RequestMethod.POST)
	public Response selectRewardInfo(@RequestBody SOMap param) throws Exception {
		return new Response(mypageService.selectRewardInfo(param));
	}
	/**********************
	 * 찜한 상품 전체 삭제
	 ********************/
	@RequestMapping(value = "/wish/alldel",method = RequestMethod.POST)
	public Response deleteMyWishAllDel(@RequestBody SOMap param) throws Exception {
		return mypageService.deleteMyWishAllDel(param);
	}
	/**********************
	 * 찜한 상품 카트에 담기
	 ********************/
	@RequestMapping(value = "/wish/addCart",method = RequestMethod.POST)
	public Response insertWishAddCart(@RequestBody SOMap param) throws Exception {
		return mypageService.insertWishAddCart(param);
	}
	
	/**********************
	 * 배송지 목록
	 ********************/
	/**
	 * 배송지 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/address/list", method = RequestMethod.POST)
	public Response selectAddressList(@RequestBody SOMap param) throws Exception {
		return mypageService.selectAddressList(param);
	}
	
	/**********************
	 * 배송지 정보
	 ********************/
	/**
	 * 배송지 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/address/detail", method = RequestMethod.POST)
	public Response selectAddress(@RequestBody SOMap param) throws Exception {
		return mypageService.selectAddress(param);
	}
	
	/**
	 * 배송지 정보 저장
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/address/save", method = RequestMethod.POST)
	public Response saveAddressInfo(@RequestBody SOMap param) throws Exception {
		return mypageService.saveAddressInfo(param);
	}
	
	/**
	 * 배송지 정보 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/address/del", method = RequestMethod.POST)
	public Response delAddressInfo(@RequestBody SOMap param) throws Exception {
		return mypageService.delAddressInfo(param);
	}
	/**********************
	 * 회원정보 수정
	 ********************/
	/**
	 * 회원정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/list", method = RequestMethod.POST)
	public Response selectUserInfo(@RequestBody SOMap param) throws Exception {
		return mypageService.selectUserInfo(param);
	}
	
	/**
	 * 회원정보 저장
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/save", method=RequestMethod.POST)
	public Response saveUserInfo(@RequestBody SOMap param) throws Exception {
		return mypageService.saveUserInfo(param);
	}
	
	/**
	 * 회원정보 체크
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/check", method = RequestMethod.POST)
	public Response selectUserCheck(@RequestBody SOMap param) throws Exception {
		return mypageService.selectUserCheck(param);
	}
	
	/**
	 * as 신청
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/apply", method = RequestMethod.POST)
	public Response applyAs(@RequestBody SOMap param) throws Exception {
		return new Response(mypageService.applyAs(param));
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
		return new Response(mypageService.applySave(params, uploadFile));
	}
	
	/**
	 * as 신청목록 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/applylist", method = RequestMethod.POST)
	public Response applyList(@RequestBody SOMap param) throws Exception {
		SOMap result = mypageService.applyList(param);
		result.put("myorder", mypageService.applyMyOrder(param));
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
		return new Response(mypageService.applyDetail(param));
	}
	
	/**
	 * as 신청목록 상세
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/as/applydelete", method = RequestMethod.POST)
	public Response applyDelete(@RequestBody SOMap param) throws Exception {
		return new Response(mypageService.applyDelete(param));
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
	 * 프로모션 코드 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addPromotionCode",  method = RequestMethod.POST)
	public Response addPromotionCode(@RequestBody SOMap params) throws Exception {
		return new Response(mypageService.addPromotionCode(params));
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
		return new Response(mypageService.applyRevise(params, uploadFile));
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
		result.put("list", mypageService.applyMyOrder(params));
		return new Response(result);
	}
	
	/**
	 * 배송추적
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/tracking", method = RequestMethod.POST)
	public Response selectDelivery(@RequestBody SOMap param) throws Exception {
		SOMap result = mypageService.selectDeliveryTracking(param);
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
		SOMap result = mypageService.selectOrderList(param);
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
		SOMap result = mypageService.selectOrderDetail(param);
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
		SOMap result =  mypageService.selectClaimList(param);
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
		SOMap result = mypageService.selectClaimApply(params);
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
		SOMap result = mypageService.calClaimApply(params);
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
		SOMap result = mypageService.detailClaim(params);
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
		SOMap result = mypageService.saveClaimApply(mReq.getFileMap(), params);
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
		
		SOMap result = mypageService.saveClaimPayment(params);
		
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
		SOMap result = mypageService.cancelClaim(params);
		
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
		SOMap result = mypageService.updateOrderRcvInfo(params);
		return new Response(result);
	}
	
	/**
	 * 이전 주문내역조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/preorder",  method=RequestMethod.POST)
	public Response preOrder(@RequestBody SOMap param) throws Exception {
		return new Response(mypageService.selectPreOrder(param));
	}
	
	/**
	 * 클레임 정보
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	@RequestMapping(value="/claim/info", method=RequestMethod.POST)
	public Response claimInfo(@RequestBody SOMap params) throws Exception{
		SOMap result = mypageService.selectClaimInfo(params);
		
		return new Response(result);
	}
}
