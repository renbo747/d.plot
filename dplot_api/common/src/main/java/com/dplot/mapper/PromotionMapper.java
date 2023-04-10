package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 프로모션 Mapper (T_PROMOTION)
 * @fileName	: PromotionMapper.java
 * @author		: JSK
 * @date		: 2021.12.28
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.28	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface PromotionMapper {
	
	/**
	 * 프로모션 목록 조회
     * @param param
     * @return List<SOMap>
	 */
	List<SOMap> selectPromotionList(SOMap params);
	
	/**
	 * 프로모션 목록 건수 조회
     * @param param
     * @return SOMap
	 */
	SOMap selectPromotionListCount(SOMap params);
	
	/**
	 * 프로모션 목록 조회(엑셀용)
     * @param param
     * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> selectPromotionListForExcel(SOMap params);
	
	/**
	 * 프로모션 상세 조회
     * @param param
     * @return SOMap
	 */
	SOMap selectPromotionDetail(SOMap params);
	
	/**
	 * 프로모션 저장
     * @param param
     * @return int
	 */
	int insertPromotion(SOMap params);
	
	/**
	 * 프로모션 수정
     * @param param
     * @return int
	 */
	int updatePromotion(SOMap params);
	
	/**
	 * 프로모션 사용여부 갱신
     * @param param
     * @return int
	 */
	int updatePromotionUse(SOMap params);

	/**
	 * ERP 전송데이터
	 */
	List<SOMap> selectPromotionERPData(SOMap param);

	/**
	 * 관리자 대시보드 용 프로모션 데이터
	 */
	SOMap selectPromotionDashBoard(SOMap param);
	
	/**
	 * 구매확정 적립금프로모션 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectOrderConfirmPromotion(SOMap param);
}
