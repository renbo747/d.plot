package com.dplot.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 공통쿠폰회원발급사용이력 Mapper (T_COMCOUPON_MEMISSUE)
 * @fileName	: CouponMemissueMapper.java
 * @author		: JSK
 * @date		: 2022.01.19
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.01.19	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface CouponMemissueMapper {
	
	/**
	 * 쿠폰회원발급사용이력 목록조회
     * @param params
     * @return List<SOMap>
	 */
	List<SOMap> selectCouponMemissueList(SOMap params);
	
	/**
	 * 쿠폰회원발급사용이력 건수 조회
     * @param params
     * @return int
	 */
	int selectCouponMemissueListCount(SOMap params);
	
	/**
	 * 발급/사용내역 조회(엑셀용)
     * @param params
     * @return List<SOMap>
	 */
	List<Map<String, Object>> selectMemissueListForExcel(HashMap<String, Object> params);
	
	/**
	 * 쿠폰회원발급사용이력 삭제처리(삭제여부 T)
     * @param params
     * @return int
	 */
	int updateCouponMemissueUse(SOMap params);

	/**
	 * 관리자 회원 쿠폰리스트 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectCouponListForAdmin(SOMap params);

	/**
	 * 관리자 회원 쿠폰리스트 카운트 조회
	 * @param params
	 * @return
	 */
	SOMap selectCouponListCountForAdmin(SOMap params);
	
	/**
	 * 쿠폰목록조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectCouponListByUser(SOMap params);
	
	/**
	 * 상품별쿠폰조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectCouponListByGoods(SOMap params);
	
	/**
	 * 쿠폰 다운로드 체크항목조회
	 * @param params
	 * @return
	 */
	SOMap selectCouponChkInfo(SOMap params);
	
	/**
	 * 쿠폰 다운로드 
     * @param params
     * @return int
	 */
	int updateCouponDownload(SOMap params);
	
	/**
	 * 쿠폰 사용
     * @param params
     * @return int
	 */
	int updateCouponUsed(SOMap params);
	
	/**
	 * 쿠폰 사용취소
	 * @param params
	 * @return
	 */
	int updateCouponUsedCancel(SOMap params);
	
	/**
	 * 공통쿠폰회원발급사용이력 저장
     * @param params
     * @return int
	 */
	int insertCouponMemissue(SOMap params);

	SOMap selectLastUsedCoupon(SOMap params);

	SOMap selectLastIssuedCoupon(SOMap params);

	/**
	 * 회원가입시 지급 쿠폰 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectSignUpCoupon(SOMap param);
	
	/**
	 * 공통쿠폰 회원발급사용이력 조회(By COMCPNIDX)
	 * @param param
	 * @return
	 */
	List<SOMap> selectCouponListByCpnidx(SOMap param);

	/**
	 * 쿠폰 사용임박, 사용가능 수 조회
	 * @param param
	 * @return
	 */
	SOMap selectCouponCntInfo(SOMap param);
	
	/**
	 * 쿠폰 다운로드 정보
	 * @param param
	 * @return
	 */
	SOMap selectCouponInfo(SOMap param);
}
