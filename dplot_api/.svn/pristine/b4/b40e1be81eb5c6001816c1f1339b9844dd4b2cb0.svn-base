package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 공통쿠폰발급이력 Mapper (T_COMCOUPON_ISSUE)
 * @fileName	: CouponIssueMapper.java
 * @author		: JSK
 * @date		: 2022.01.19
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.01.19	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface CouponIssueMapper {
	
	/**
	 * 쿠폰발급이력 조회
     * @param params
     * @return List<SOMap>
	 */
	List<SOMap> selectCouponIssueList(SOMap params);
	
	/**
	 * 쿠폰발급이력 건수 조회
     * @param params
     * @return int
	 */
	int selectCouponIssueCnt(SOMap params);
	
	/**
	 * 쿠폰 최근발급이력 조회
     * @param params
     * @return SOMap
	 */
	SOMap selectLastCouponIssueDetail(SOMap params);
	
	/**
	 * 쿠폰발급이력 저장
     * @param params
     * @return int
	 */
	int insertCouponIssue(SOMap params);
}
