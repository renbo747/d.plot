package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 공통쿠폰구매확정카테고리 Mapper (T_COMCOUPON_ORD_CATE)
 * @fileName	: CouponOrdCateMapper.java
 * @author		: JSK
 * @date		: 2022.01.19
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.01.19	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface CouponOrdCateMapper {
	
	/**
	 * 쿠폰 구매확정 카테고리 목록조회
     * @param params
     * @return List<SOMap>
	 */
	List<SOMap> selectCouponOrdCateList(SOMap params);
	
	/**
	 * 쿠폰 구매확정 카테고리 저장
     * @param params
     * @return int
	 */
	int insertCouponOrdCate(SOMap params);
	
	/**
	 * 쿠폰 구매확정 카테고리 삭제
     * @param params
     * @return int
	 */
	int deleteCouponOrdCate(SOMap params);

	/**
	 * ERP 전송용
	 */
	List<SOMap> selectCouponOrdCateERPData(SOMap param);
}
