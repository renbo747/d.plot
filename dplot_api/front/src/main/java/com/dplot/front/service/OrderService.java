package com.dplot.front.service;

import com.dplot.common.SOMap;

public interface OrderService {
	
	/**
	 * 결재할 주문목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap selectOrderList(SOMap param) throws Exception;
	
	/**
	 * 주문계산
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap calOrderAmt(SOMap param) throws Exception;
	
	/**
	 * 주문번호 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap selectOrderNo(SOMap param) throws Exception;
	
	/**
	 * 주문정보 저장
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap saveOrder(SOMap param) throws Exception;

	/**
	 * 주문 구매확정 처리
	 * @param params
	 * @return
	 */
	public SOMap selectOrderDetail(SOMap params) throws Exception;
	
	
	/**
	 * 비회원주문 전화번호 인증발송
	 * @param param
	 * @throws Exception
	 */
	public SOMap sendAuth(SOMap param) throws Exception;
	
	/**
	 * 비회원주문 전화번호 인증번호 확인
	 * @param param
	 * @throws Exception
	 */
	public void confirmAuth(SOMap param) throws Exception;
	
	/**
	 * 카드혜택조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap cardPromotion(SOMap param) throws Exception;
}
