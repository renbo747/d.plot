package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

/**
 * @discription	: 공통쿠폰발급쿠폰 Mapper (T_COMCOUPON_INFO)
 * @fileName	: CouponInfoMapper.java
 * @author		: JSK
 * @date		: 2022.02.08
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.02.08	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface CouponInfoMapper {
	/**
	 * 쿠폰정보 저장
     * @param param
     * @return int
	 */
	int insertCouponInfo(SOMap params);
	
	/**
	 * 쿠폰정보 수정
     * @param param
     * @return int
	 */
	int updateCouponInfo(SOMap params);
	
	/**
	 * 쿠폰정보 삭제 (정기발급 쿠폰 수정시 사용)
     * @param param
     * @return int
	 */
	int deleteCouponInfo(SOMap params);
	
	/**
	 * 쿠폰 발급상태 갱신
     * @param params
     * @return int
	 */
	int updateCouponIssueSt(SOMap params);
	
	/**
	 * 현재 쿠폰발급상태 조회
     * @param param
     * @return SOMap
	 */
	SOMap getCouponIssueSt(SOMap params);
	
	/**
	 * 쿠폰 삭제구분 갱신
     * @param params
     * @return int
	 */
	int updateCouponCpnDelType(SOMap params);

	/**
	 * 쿠폰발급정보 리스트
	 * @param params
	 * @return
	 */
	List<SOMap> selectCouponInfoERPData(SOMap params);
}
