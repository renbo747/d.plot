package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;

/**
 * @discription	: 쇼핑전시영역1 Service
 * @fileName	: ShopExOneService.java
 * @author		: LKW
 * @date		: 2022.04.04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.04	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface ShopExOneService {

	/**
	 * 쇼핑전시영역1 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectShopExOneList(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 사용여부, 전시여부 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateShopExOne(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectShopExOneExcelList(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveShopExOne(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 변경시 알람용 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkShopExOne(SOMap params) throws Exception;

}
