package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 프로모션 카테고리 Mapper (T_PROMO_CATE)
 * @fileName	: PromoCateMapper.java
 * @author		: JSK
 * @date		: 2021.12.28
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.28	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface PromoCateMapper {
	
	/**
	 * 프로모션 카테고리 목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
	 */
	List<SOMap> selectPromoCateList(SOMap params);
	
	/**
	 * 프로모션 카테고리 저장
     * @param param
     * @return int
     * @throws Exception
	 */
	int insertPromoCate(SOMap params);
	
	/**
	 * 프로모션 카테고리 삭제
     * @param param
     * @return int
     * @throws Exception
	 */
	int deletePromoCate(SOMap params);

	/**
	 * ERP 전송용
	 */
	List<SOMap> selectPromoCateERPData(SOMap params);
}
