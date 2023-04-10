package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

/**
 * @discription	: 전시영역5 Service
 * @fileName	: ShopExFiveService.java
 * @author		: LKW
 * @date		: 2022.04.06
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.06	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface ShopExFiveService {

	/**
	 * 전시영역5 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectShopExFiveList(SOMap params) throws Exception;

	/**
	 * 전시영역5 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectShopExFiveExcelList(SOMap params) throws Exception;

	/**
	 * 전시영역5 사용여부, 전시여부 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateShopExFive(SOMap params) throws Exception;

	/**
	 * 전시영역5 수정
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap modifyShopExFive(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 전시영역5 상세조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectShopExFiveDetail(SOMap params) throws Exception;

	/**
	 * 전시영역5 전시조건 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectShopExFiveCheck(SOMap params) throws Exception;

	/**
	 * 전시영역5 저장
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap saveShopExFive(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;


}
