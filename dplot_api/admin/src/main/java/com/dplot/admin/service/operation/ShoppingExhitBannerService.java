package com.dplot.admin.service.operation;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

/**
 * @discription	: 전시영역배너 Service
 * @fileName	: ShoppingExhitBannerService.java
 * @author		: LKW
 * @date		: 2022.04.05
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.04	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface ShoppingExhitBannerService {

	/**
	 * 전시영역배너 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectExhitBannerList(SOMap params) throws Exception;

	/**
	 * 전시영역배너 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateExhitBanner(SOMap params) throws Exception;

	/**
	 * 전시영역배너 저장
	 * @param params
	 * @param uploadFile 
	 * @return
	 * @throws Exception
	 */
	SOMap saveExhitBanner(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 전시영역배너 수정
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap modifyExhitBanner(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 전시영역배너 상세 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectExhitBannerDetail(SOMap params) throws Exception;

	/**
	 * 전시영역배너 변경 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkExhitBanner(SOMap params) throws Exception;
}
