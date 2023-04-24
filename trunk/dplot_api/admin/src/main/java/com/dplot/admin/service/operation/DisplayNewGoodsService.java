package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 신상품 Service
 * @fileName	: Service.java
 * @author		: LKW
 * @date		: 2021.12.06
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.06	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface DisplayNewGoodsService {

	/**
	 * 기본정보 - 신상품 조회
	 * @return SOMap
	 * @throws Exception
	 */
	SOMap selectNewGoodsList(SOMap params) throws Exception;

	/**
	 * 신상품 수정
	 * @param params
	 * @return
	 */
	SOMap updateNewGoodsList(SOMap params) throws Exception;

	/**
	 * 신상품 추가
	 * @param params
	 * @return
	 */
	SOMap insertNewGoodsList(SOMap params) throws Exception;

	/**
	 * 신상품 엑셀 다운로드
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectNewGoodsExcelList(SOMap params) throws Exception;
}
