package com.dplot.front.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.Response;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : MypageService.java
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
public interface MypageService {
	
	/**
	 * 마이페이지 메인 조회
	 * @param param
	 * @return
	 */
	Response selectMypageInfo(SOMap param) throws Exception;
	
	/**
	 * 마이페이지 적립금 내역 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Response selectReserveList(SOMap param) throws Exception;

	/**
	 * 마이페이지_배송지 목록정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Response selectAddressList(SOMap param) throws Exception;
	
	/**
	 * 마이페이지_배송지 정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Response selectAddress(SOMap param) throws Exception;

	/**
	 * 마이페이지_배송지 정보 저장
	 * @param param
	 * @return
	 */
	Response saveAddressInfo(SOMap param) throws Exception;

	/**
	 * 마이페이지_배송지 정보 삭제
	 * @param param
	 * @return
	 */
	Response delAddressInfo(SOMap param) throws Exception;

	/**
	 * 회원정보 조회
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	Response selectUserInfo(SOMap param) throws Exception;

	/**
	 * 회원정보 확인
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	Response selectUserCheck(SOMap param) throws Exception;
	
	/**
	 * 회원정보 저장
	 * @param param
	 * @return
	 */
	Response saveUserInfo(SOMap param) throws Exception;

	/**
	 * 마이페이지_찜한 목록 전체 삭제
	 * @param param
	 * @return
	 */
	Response deleteMyWishAllDel(SOMap param) throws Exception;

	/**
	 * 마이페이지_찜한 상품 카트에 담기
	 * @param param
	 * @return
	 */
	Response insertWishAddCart(SOMap param) throws Exception;

	/**
	 * 마이페이지_등급조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap selectMyGradeInfo(SOMap param) throws Exception;

	/**
	 * 마이페이지_메시지 목록 조회
	 * @param param
	 * @return
	 */
	SOMap selectMyMsgList(SOMap param) throws Exception;

	/**
	 * 마이페이지_메시지 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap deleteMyMsg(SOMap param) throws Exception;
	
	/** AS 신청
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap applyAs(SOMap param) throws Exception;

	/**AS 신청 완료
	 * @param param
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap applySave(SOMap param, Map<String, MultipartFile> uploadFile) throws Exception;
	
	/**AS 수정 완료
	 * @param param
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap applyRevise(SOMap param, Map<String, MultipartFile> uploadFile) throws Exception;
	
	/**AS신청목록 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap applyList(SOMap param) throws Exception;
	
	/**AS신청목록 리스트 총 갯수
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int applyListCount(SOMap param) throws Exception;
	
	/**AS 신청목록 상세
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap applyDetail(SOMap param) throws Exception;
	
	/**AS 신청 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap applyDelete(SOMap param) throws Exception;
	
	/**
	 * 프로모션코드 등록 처리
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap addPromotionCode(SOMap params) throws Exception;
	
	/**AS 신청 - 내 주문 신청목록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<SOMap> applyMyOrder(SOMap params) throws Exception;
	

	/**
	 * e-포인트 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Response selectEpointList(SOMap param) throws Exception;
	
	/**
	 * 친구초대 리워드 보상정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap selectRewardInfo(SOMap param) throws Exception;

	/**
	 * 회원 적립금, 포인트, 쿠폰 지급내역 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap selectMyPayInfo(SOMap param) throws Exception;
	
	/**
	 * 마이페이지 주문/배송 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap selectDeliveryTracking(SOMap param) throws Exception;
	
	/**
	 * 마이페이지_주문/배송 내역 조회
	 * @param param
	 * @return
	 */
	SOMap selectOrderList(SOMap param) throws Exception;
	
	/**
	 * 마이페이지_주문상세 조회
	 * @param param
	 * @return
	 */
	SOMap selectOrderDetail(SOMap param) throws Exception;
	
	/**
	 * 마이페이지_클레임목록 조회
	 * @param param
	 * @return
	 */
	SOMap selectClaimList(SOMap param) throws Exception;
	
	
	/**
	 * 마이페이지_클레임신청조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectClaimApply(SOMap params) throws Exception;
	
	/**
	 * 마이페이지_클레임신청 재계산
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap calClaimApply(SOMap params) throws Exception;
	
	/**
	 * 마이페이지_클레임신청 저장
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveClaimApply(Map<String, MultipartFile> files, SOMap params) throws Exception;
	
	/**
	 * 마이페이지_클레임상세
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap detailClaim(SOMap params) throws Exception;
	
	/**
	 * 마이페이지_클레임결제처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap saveClaimPayment(SOMap param) throws Exception;
	
	/**
	 * 마이페이지_클레임철회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap cancelClaim(SOMap param) throws Exception;
	
	/**
	 * 주문배송지 변경
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateOrderRcvInfo(SOMap params) throws Exception;

	/**
	 * 리워드 지급내역 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap rewardpayList(SOMap param) throws Exception;

	/**
	 * 이전주문내역 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap selectPreOrder(SOMap param) throws Exception;
	
	/**
	 * 클레임 정보조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectClaimInfo(SOMap params) throws Exception;
}
