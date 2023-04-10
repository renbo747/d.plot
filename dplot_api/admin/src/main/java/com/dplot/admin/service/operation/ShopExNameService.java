package com.dplot.admin.service.operation;

import com.dplot.common.SOMap;

/**
 * @discription	: 쇼핑메인전시영역명칭 Service
 * @fileName	: ShopExNameService.java
 * @author		: LKW
 * @date		: 2022.04.04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.04	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface ShopExNameService {

	/**
	 * 매거진트렌트 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectShopExNameList(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveShopExName(SOMap params) throws Exception;
}
