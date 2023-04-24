package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;

/**
 * @discription	: 쇼핑전시영역2 Service
 * @fileName	: ShopExTwoService.java
 * @author		: LKW
 * @date		: 2022.04.06
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.06	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface ShopExTwoService {

	/**
	 * 쇼핑전시영역2 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectShopExTwoList(SOMap params) throws Exception;

	/**
	 * 쇼핑전시영역2  사용여부, 전시여부 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateShopExTwo(SOMap params) throws Exception;

	/**
	 * 쇼핑전시영역2  엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectShopExTwoExcelList(SOMap params) throws Exception;

	/**
	 * 쇼핑전시영역2  등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveShopExTwo(SOMap params) throws Exception;

	/**
	 * 쇼핑전시영역2  변경시 알람용 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkShopExTwo(SOMap params) throws Exception;

}
