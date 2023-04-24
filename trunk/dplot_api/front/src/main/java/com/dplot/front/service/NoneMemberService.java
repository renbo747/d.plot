package com.dplot.front.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.Response;
import com.dplot.common.SOMap;

public interface NoneMemberService {
	/**
	 * 비회원 메인 조회
	 * @param param
	 * @return
	 */
	Response selectNoneMemberInfo(SOMap param) throws Exception;

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
	
	/**AS 신청 - 내 주문 신청목록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<SOMap> applyMyOrder(SOMap params) throws Exception;
	
	/**
	 * 비회원_주문/배송 내역 조회
	 * @param param
	 * @return
	 */
	SOMap selectOrderList(SOMap param) throws Exception;
	
	/**
	 * 비회원_주문상세 조회
	 * @param param
	 * @return
	 */
	SOMap selectOrderDetail(SOMap param) throws Exception;
	
	/**
	 * 비회원_클레임목록 조회
	 * @param param
	 * @return
	 */
	SOMap selectClaimList(SOMap param) throws Exception;
	
	
	/**
	 * 비회원_클레임신청조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectClaimApply(SOMap params) throws Exception;
	
	/**
	 * 비회원_클레임신청 재계산
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap calClaimApply(SOMap params) throws Exception;
	
	/**
	 * 비회원_클레임신청 저장
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveClaimApply(Map<String, MultipartFile> files, SOMap params) throws Exception;
	
	/**
	 * 비회원_클레임상세
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap detailClaim(SOMap params) throws Exception;
	
	/**
	 * 비회원_클레임결제처리
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
	 * 클레임 정보조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectClaimInfo(SOMap params) throws Exception;
	
}
