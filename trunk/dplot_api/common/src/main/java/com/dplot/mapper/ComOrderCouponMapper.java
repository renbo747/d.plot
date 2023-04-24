package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : ComOrderCouponMapper.java
 * @Project : datapick_api
 * @Date : 2022. 1. 26. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 1. 26.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface ComOrderCouponMapper {

	/**
	 * 주문쿠폰 저장
	 * @param dbParam
	 * @return
	 */
	int insertComOrderCoupon(SOMap dbParam);

	/**
	 * 주문쿠폰 목록조회
	 * @param dbParam
	 * @return List<SOMap>
	 */
	List<SOMap> selectOrderCouponList(SOMap dbParam);
	
	/**
	 * 관리자-주문할인내역 조회
	 * @param dbParam
	 * @return List<SOMap>
	 */
	List<SOMap> selectAdminOrderDiscountList(SOMap dbParam);

	/**
	 * ERP 전송용 주문 쿠폰 데이터 조회
	 */
	List<SOMap> selectOrderCouponERPData(SOMap param);
}
