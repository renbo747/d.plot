package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;

/**
 * @discription	: 구독관리 Service
 * @fileName	: OperSubscribeService.java
 * @author		: LKW
 * @date		: 2022.04.08
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.08	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface OperSubscribeService {

	/**
	 * 구독관리 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectSubscribeList(SOMap params) throws Exception;

	/**
	 * 구독관리 구독취소
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateSubscribe(SOMap params) throws Exception;

	/**
	 * 구독관리 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectSubscribeExcelList(SOMap params) throws Exception;

}
