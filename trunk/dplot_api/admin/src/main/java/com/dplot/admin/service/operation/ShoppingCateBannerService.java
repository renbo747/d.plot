package com.dplot.admin.service.operation;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 쇼핑카테고리배너 Service
 * @fileName	: ShoppingCateBannerService.java
 * @author		: LKW
 * @date		: 2022.04.04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.04	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface ShoppingCateBannerService {

	/**
	 * 카테고리배너 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectCateBannerList(SOMap params) throws Exception;

	/**
	 * 카테고리배너 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateCateBanner(SOMap params) throws Exception;

	/**
	 * 카테고리배너 저장
	 * @param params
	 * @param uploadFile 
	 * @return
	 * @throws Exception
	 */
	SOMap saveCateBanner(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 카테고리배너 수정
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap modifyCateBanner(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 카테고리배너 상세 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectCateBannerDetail(SOMap params) throws Exception;

	/**
	 * 카테고리배너 변경 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkCateBanner(SOMap params) throws Exception;

	/**
	 * 1depth 카테고리 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectCategoryList(SOMap params) throws Exception;
}
