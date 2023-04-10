package com.dplot.admin.service.promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

public interface AdminGiftService {
    /**
     * 사은품 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectGiftList(SOMap params) throws Exception;

    /**
     * 사은품 사용여부 수정
     * @param params
     * @return
     */
	SOMap updateGiftUse(SOMap params) throws Exception;

	/**
	 * 사은품 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectGiftExcelList(SOMap params) throws Exception;

	/**
	 * 사은품 저장
	 * @param params
	 * @param uploadFile
	 * @return
	 */
	SOMap saveGift(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;
	
	/**
	 * 사은품 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectGiftDetail(SOMap params) throws Exception;

	/**
	 * 사은품 수정
	 * @param params
	 * @param uploadFile
	 * @return
	 */
	SOMap modifyGift(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;
}
