package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

/**
 * @discription	: 전시영역3 Service
 * @fileName	: ShopExThreeService.java
 * @author		: LKW
 * @date		: 2022.04.06
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.06	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface ShopExThreeService {

	/**
	 * 전시영역3관리 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectShopExThreeList(SOMap params) throws Exception;

	/**
	 * 전시영역3관리 사용여부 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateShopExThree(SOMap params) throws Exception;

	/**
	 * 전시영역3관리 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectShopExThreeExcelList(SOMap params) throws Exception;

	/**
	 * 전시영역3관리 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveShopExThree(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 전시영역3관리 상세조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectShopExThreeDetail(SOMap params) throws Exception;

	/**
	 * 전시영역3관리 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap modifyShopExThree(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;
	
	/**
	 * 전시영역3관리 변경시 알람용 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkShopExThree(SOMap params) throws Exception;

}
