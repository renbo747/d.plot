package com.dplot.admin.service.operation;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

/**
 * @discription	: 쇼핑메인배너 Service
 * @fileName	: ShoppingMainBannerService.java
 * @author		: LKW
 * @date		: 2022.04.04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.04	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface ShoppingMainBannerService {

	/**
	 * 메인배너 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectMainBannerList(SOMap params) throws Exception;

	/**
	 * 메인배너 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateMainBanner(SOMap params) throws Exception;

	/**
	 * 메인배너 저장
	 * @param params
	 * @param uploadFile 
	 * @return
	 * @throws Exception
	 */
	SOMap saveMainBanner(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 메인배너 수정
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap modifyBanner(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 메인배너 상세 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectBannerDetail(SOMap params) throws Exception;

	/**
	 * 메인배너 변경 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkBanner(SOMap params) throws Exception;
}
