package com.dplot.admin.service.operation;

import com.dplot.common.SOMap;

/**
 * @discription	: 검색창링크 Service
 * @fileName	: OperationSearchLinkService.java
 * @author		: LKW
 * @date		: 2022.04.12
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.12	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface OperationSearchLinkService {

	/**
	 * 검색창링크 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectSearchLink(SOMap params) throws Exception;

	/**
	 * 검색창링크 저장
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveSearchLink(SOMap params) throws Exception;

}
