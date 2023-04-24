package com.dplot.admin.service.operation;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

/**
 * @discription	: 스플래시이미지 Service
 * @fileName	: OperSplashImgService.java
 * @author		: LKW
 * @date		: 2022.04.08
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.08	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface OperSplashImgService {

	/**
	 * 스플래시이미지 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectSplashImgList(SOMap params) throws Exception;

	/**
	 * 스플래시이미지 저장
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveSplashImg(SOMap params, Map<String, MultipartFile> files) throws Exception;

}
