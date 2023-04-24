package com.dplot.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 *
 * @FileName : CouponMapper.java
 * @Project : datapick_api
 * @Date : 2021. 12. 15.
 * @Author : KTH
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 12. 15.			LCK			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface CouponMapper {

	/**
	 * 쿠폰 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectCouponList(SOMap params);

	/**
	 * 쿠폰 상태별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectCouponStateList(SOMap params);

	/**
	 * 쿠폰 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectCouponListForExcel(HashMap<String, Object> params);

	/**
	 * 쿠폰 상세 조회
     * @param param
     * @return SOMap
	 */
	SOMap selectCouponDetail(SOMap params);

	/**
	 * 쿠폰 저장
     * @param param
     * @return int
	 */
	int insertCoupon(SOMap params);

	/**
	 * 쿠폰 수정
     * @param param
     * @return int
	 */
	int updateCoupon(SOMap params);

	/**
	 * 회원(프로모션)용 쿠폰 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectPromotionCouponList(SOMap params);

	/**
	 * 쿠폰번호로 쿠폰정보조회
	 * @param dbParam
	 * @return
	 */
	SOMap selectCouponByComcpnidx(SOMap dbParam);

	/**
	 * 쿠폰정보 조회
	 * @param dbParam
	 * @return
	 */
	SOMap selectCouponInfo(SOMap dbParam);

	/**
	 * 회원가입 쿠폰 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectSignupCoupon(SOMap param);

	/**
	 * 상품 구매확정 쿠폰 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectOrderConfirmCoupon(SOMap params);

	/**
	 * 상품 다운로드 쿠폰 가능수량 조회
	 * @param params
	 * @return
	 */
	int selectGoodsCouponCnt(SOMap params);

	/**
	 * 쿠폰 ERP 전송데이터
	 */
	List<SOMap> selectCouponERPData(SOMap params);

	/**
	 * 대시보드용 쿠폰 데이터
	 */
	SOMap selectCouponDashBoard(SOMap param);

	/**
	 * 쿠폰 사용여부 수정
	 * @param params
	 * @return SOMap
	 */
	int updateCouponUse(SOMap params);

	/**
	 * 평생회원 쿠폰 정보 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectLifeCoupon(SOMap params);

	/**
	 * 해당상품의 신규회원가입쿠폰 존재여부
	 *
	 * @param params
	 * @return
	 */
	Integer newJoinCouponSaleAmt(SOMap params);
	
	/**
	 * 쿠폰 다운/사용 수량 조회
	 * @param param
	 * @return
	 */
	SOMap getDownUseCount(SOMap param);
	
	/**
	 * 상품쿠폰 즉시할인 사용 수량 조회
	 * @param param
	 * @return
	 */
	SOMap getDownGoodsUseCount(SOMap param);
}
