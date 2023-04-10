package com.dplot.admin.service.promotion;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;

/**
 * @discription	: 프로모션 적립금 Service
 * @fileName	: AdminReserveService.java
 * @author		: JSK
 * @date		: 2021.12.20
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.20	JSK			최초생성
 * -----------------------------------------------------------------
 */
public interface AdminReserveService {

    /**
     * 적립금 목록 조회
     *
     * @param params
     * @return SOMap
     * @throws Exception
     */
    SOMap selectReserveList(SOMap params) throws Exception;

    /**
     * 적립금 목록 조회(엑셀용)
     *
     * @param params
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    List<Map<String, Object>> selectReserveListForExcel(SOMap params) throws Exception;
    
	/**
	 * 적립금 저장
	 * 
	 * @param params
	 * @return int
	 * @throws Exception
	 */
    int saveReserve(SOMap params) throws Exception;
    
	/**
	 * 적립금차감 저장
	 * 
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
    SOMap saveReserveDeduction(SOMap params) throws Exception;

    /**
     * 적립금 자동지급설정 조회
     *
     * @return SOMap
     * @throws Exception
     */
    SOMap selectReserveConfig() throws Exception;
    
	/**
	 * 적립금 자동지급설정 저장
	 * 
	 * @param params
	 * @return int
	 * @throws Exception
	 */
    int saveReserveConfig(SOMap params) throws Exception;
}
