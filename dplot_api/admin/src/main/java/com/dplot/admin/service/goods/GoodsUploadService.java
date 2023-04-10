package com.dplot.admin.service.goods;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

/**
 * @discription	: 상품 일괄등록 Service
 * @fileName	: GoodsUploadService.java
 * @author		: JSK
 * @date		: 2022.07.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.07.10	JSK			최초생성
 * -----------------------------------------------------------------
 */
public interface GoodsUploadService {

	/**
	 * 상품일괄업로드 코드조회
	 * @param params
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	List<Map<String, Object>> selectCodeListForExcel(SOMap params) throws Exception;

	/**
	 * 상품일괄업로드 목록조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	SOMap selectGoodsExcelList(SOMap params) throws Exception;

    /**
     * 상품일괄업로드 엑셀 업로드
     * @param params
     * @param uploadFiles
     * @return SOMap
     * @throws Exception
     */
	SOMap uploadGoodsExcel(SOMap params, Map<String, MultipartFile> uploadFiles) throws Exception;
	
    /**
     * 상품일괄업로드 이미지 업로드
     * @param params
     * @return int
     * @throws Exception
     */
	int uploadGoodsImage(SOMap params) throws Exception;
}
