package com.dplot.common.service;

import java.util.List;

import com.dplot.common.SOMap;

public interface TossService {
	
	/**
	 * 토스결제승인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap tossPayment(SOMap param) throws Exception;
	
	/**
	 * 토스결제취소
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap tossPaymentCancel(SOMap param) throws Exception;
	
	
	/**
	 * 토스결제조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<SOMap> tossPaymentList(SOMap param) throws Exception;
	
	/**
	 * 카드혜택조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap tossCardPromotion(SOMap param) throws Exception;
	
	/**
	 * 현금영수증 발급
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap tossCashReceipts(SOMap param) throws Exception;
	
	/**
	 * 현금영수증 취소
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap tossCashReceiptsCancel(SOMap param) throws Exception;
}
