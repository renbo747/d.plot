package com.dplot.admin.service.promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;

public interface AdminCouponService {
    /**
     * 쿠폰 목록 조회
     *
     * @param params
     * @return SOMap
     * @throws Exception
     */
    SOMap selectCouponList(SOMap params) throws Exception;

	/**
	 * 쿠폰목록 조회 (엑셀용)
	 * @param params
	 * @return List<Map<String, Object>>
     * @throws Exception
	 */
	List<Map<String, Object>> selectCouponListForExcel(HashMap<String, Object> params) throws Exception;

    /**
     * 쿠폰 상세정보 조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    SOMap selectCouponInfo(SOMap params) throws Exception;
    
	/**
	 * 쿠폰 저장
	 * @param params
	 * @return int
	 * @throws Exception
	 */
    int saveCoupon(SOMap params) throws Exception;
    
	/**
	 * 쿠폰 수정
	 * @param params
	 * @return int
	 * @throws Exception
	 */
    int updateCoupon(SOMap params) throws Exception;

    /**
     * 쿠폰 발급중지/재개 내역 조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    SOMap selectCouponIssueInfo(SOMap params) throws Exception;
    
	/**
	 * 쿠폰 발급중지/재개
	 * @param params
	 * @return int
	 * @throws Exception
	 */
    int controllIssue(SOMap params) throws Exception;

    /**
     * 쿠폰 삭제
     * @param params
     * @return int
     * @throws Exception
     */
    int removeCoupon(SOMap params) throws Exception;

	/**
	 * 발급/사용내역 조회(엑셀용)
	 * @param params
	 * @return List<Map<String, Object>>
     * @throws Exception
	 */
	List<Map<String, Object>> selectMemissueListForExcel(HashMap<String, Object> params) throws Exception;

	/**
	 * 발급/사용내역 조회
	 * @param params
	 * @return SOMap
     * @throws Exception
	 */
    SOMap selectMemissueList(SOMap params) throws Exception;
    
	/**
	 * 쿠폰 다운/사용 수량 조회
	 * @param params
	 * @return SOMap
     * @throws Exception
	 */
    SOMap getDownUseCount(SOMap params) throws Exception;
}