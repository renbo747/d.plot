package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 타임특가 Service
 * @fileName	: Service.java
 * @author		: LKW
 * @date		: 2021.12.09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.09	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface DisplayTimeService {

	/**
	 * 기본정보 - 타임특가 전체 조회
	 * @return SOMap
	 * @throws Exception
	 */
	SOMap selectTimeSpcPriceList(SOMap params) throws Exception;

	/**
	 * 타임특가 추가
	 * @param params
	 * @return
	 */
	SOMap insertTimeSpcPrice(SOMap params) throws Exception;

	/**
	 * 타임특가 수정
	 * @param params
	 * @return
	 */
	SOMap updateTimeSpcPrice(SOMap params) throws Exception;

	/** 
	 * 타임특가 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectTimeSpcPriceExcelList(SOMap params) throws Exception;

	/**
	 * 타임특가 상세 조회
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	SOMap selectTimeSpcPriceDetail(SOMap params) throws Exception;
}
