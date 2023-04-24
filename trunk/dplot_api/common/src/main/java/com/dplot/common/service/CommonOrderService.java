package com.dplot.common.service;

import java.util.List;

import com.dplot.common.SOMap;

public interface CommonOrderService {
	
	/**
	 * 주문취소 처리(입금대기)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap confirmCancel(SOMap param) throws Exception;
	
	/**
	 * 구매확정 포인트 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap selectConfirmReserve(SOMap param) throws Exception;
	
	/**
	 * 구매확정 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap confirmOrder(SOMap param) throws Exception;

	/**
	 * 수취확인(배송완료) 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap delivComplete(SOMap param) throws Exception;
	
	/**
	 * 배송지정보 수정
	 * @param params
	 * @return int
	 * @throws Exception
	 */
	public int updateOrderRcvInfo(SOMap params) throws Exception;
	
	/**
	 * 주문/결제/클레임 재계산
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap calOrderAmt(SOMap param) throws Exception;
	
	/**
	 * 배송비 계산
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void calDelivAmt(List<SOMap> list, String type) throws Exception;

	/**
	 * ERP 에서 내려온 직매입 상품 배송상태 변경 작업
	 * @param list
	 * @return
	 * @throws Exception
	 */
	int updateOrderStatusByERPData(List<SOMap> list);

	/**
	 * ERP 에서 내려온 직매입 상품 교환 배송 상태 변경 작업
	 * @param list
	 * @return
	 * @throws Exception
	 */
	int updateExchangeStatusByERPData(List<SOMap> list);

	/**
	 * ERP 에서 내려온 직매입 상품 반품 교환 회수 상태 변경 작업
	 * @param list
	 * @return
	 * @throws Exception
	 */
	int updateReturnStatusByERPData(List<SOMap> list);
}
