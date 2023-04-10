package com.dplot.admin.service.promotion;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;

/**
 * @discription	: 프로모션관리 Service
 * @fileName	: AdminPromotionService.java
 * @author		: JSK
 * @date		: 2021.12.28
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.28	JSK			최초생성
 * -----------------------------------------------------------------
 */
public interface AdminPromotionService {

    /**
     * 프로모션 목록 조회
     *
     * @param params
     * @return SOMap
     * @throws Exception
     */
    SOMap selectPromotionList(SOMap params) throws Exception;

    /**
     * 프로모션 목록 조회(엑셀용)
     *
     * @param params
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    List<Map<String, Object>> selectPromotionListForExcel(SOMap params) throws Exception;

    /**
     * 프로모션 상세정보 조회
     *
     * @param params
     * @return SOMap
     * @throws Exception
     */
    SOMap selectPromotionInfo(SOMap params) throws Exception;
    
	/**
	 * 프로모션 저장
	 * 
	 * @param params
	 * @return int
	 * @throws Exception
	 */
    int savePromotion(SOMap params) throws Exception;
    
	/**
	 * 프로모션 수정
	 * 
	 * @param params
	 * @return int
	 * @throws Exception
	 */
    int updatePromotion(SOMap params) throws Exception;

	/**
	 * 프로모션 사용여부 갱신
	 * 
	 * @param params
	 * @return int
	 * @throws Exception
	 */
    int updatePromotionUse(SOMap params) throws Exception;
}
