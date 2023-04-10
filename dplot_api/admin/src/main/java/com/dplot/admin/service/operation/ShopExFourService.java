package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

/**
 * @discription	: 전시영역4 Service
 * @fileName	: ShopExFourService.java
 * @author		: LKW
 * @date		: 2022.04.06
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.06	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface ShopExFourService {

	/**
	 * 전시영역4 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectShopExFourList(SOMap params) throws Exception;

	/**
	 * 전시영역4 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectShopExFourExcelList(SOMap params) throws Exception;

	/**
	 * 전시영역4 사용여부, 전시여부 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateShopExFour(SOMap params) throws Exception;

	/**
	 * 전시영역4 수정
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap modifyShopExFour(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 전시영역4 상세조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectShopExFourDetail(SOMap params) throws Exception;

	/**
	 * 전시영역4 전시조건 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectShopExFourCheck(SOMap params) throws Exception;

	/**
	 * 전시영역4 저장
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap saveShopExFour(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;


}
