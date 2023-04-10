package com.dplot.admin.service.goods;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

/**
 * @discription	: 프로모션배너 Service
 * @fileName	: MagazineBannerService.java
 * @author		: LKW
 * @date		: 2022.04.14
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.14	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface PromotionBannerService {

	/**
	 * 프로모션배너 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectPromotionBannerList(SOMap params) throws Exception;

	/**
	 * 프로모션배너 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updatePromotionBanner(SOMap params) throws Exception;

	/**
	 * 프로모션배너 저장
	 * @param params
	 * @param uploadFile 
	 * @return
	 * @throws Exception
	 */
	SOMap savePromotionBanner(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 프로모션배너 수정
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap modifyPromotionBanner(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 프로모션배너 상세 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectPromotionBannerDetail(SOMap params) throws Exception;

	/**
	 * 프로모션배너 변경 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkPromotionBanner(SOMap params) throws Exception;
}
