package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

/**
 * 
 * @FileName : TossreceiptMapper.java
 * @Project : datapick_api
 * @Date : 2022. 1. 26. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 1. 26.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface TossreceiptMapper {
	
	/**
	 * 토스결제내역 조회
	 * @param param
	 * @return
	 */
	SOMap selectTossreceipt(SOMap param);
	
	/**
	 * 토스결제정보 저장
	 * @param dbParam
	 * @return
	 */
	int insertTossreceipt(SOMap dbParam);

	/**
	 * ERP 전송용 결제 정보 데이터 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectOrderReceiptERPData(SOMap params);

	/**
	 *	ERP 전송용 결제 정보 클레임
	 */
	List<SOMap> selectClaimReceiptERPData(SOMap params);
}
