package com.dplot.admin.service.operation;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 배너 Service
 * @fileName	: MagazineBannerService.java
 * @author		: LKW
 * @date		: 2022.03.11
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.11	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface MagazineBannerService {

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
