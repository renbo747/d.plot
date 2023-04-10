package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 공통쿠폰대상사용자 Mapper (T_COMCOUPON_MEMBER)
 * @fileName	: CouponMemberMapper.java
 * @author		: JSK
 * @date		: 2022.01.18
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.01.18	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface CouponMemberMapper {
	
	/**
	 * 쿠폰 대상사용자 목록 조회
     * @param params
     * @return List<SOMap>
	 */
	List<SOMap> selectCouponMemberList(SOMap params);
	
	/**
	 * 쿠폰 대상사용자 저장
     * @param params
     * @return int
	 */
	int insertCouponMember(SOMap params);
	
	/**
	 * 쿠폰 대상사용자 삭제
     * @param params
     * @return int
	 */
	int deleteCouponMember(SOMap params);

	/**
	 * ERP 전송용
	 */
	List<SOMap> selectCouponMemberERPData(SOMap param);
}
