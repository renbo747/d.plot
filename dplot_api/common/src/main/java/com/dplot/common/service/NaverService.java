package com.dplot.common.service;

import com.dplot.common.SOMap;

public interface NaverService {
	
	/**
	 * 네이버페이결제승인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap naverPayment(SOMap param) throws Exception;
	
	/**
	 * 네이버페이결제취소
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap naverPaymentCancel(SOMap param) throws Exception;
	
	/**
	 * 네이버페이 현금영수증 가능금액
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap naverPaymentCashReceipts(SOMap param) throws Exception;
	
}
