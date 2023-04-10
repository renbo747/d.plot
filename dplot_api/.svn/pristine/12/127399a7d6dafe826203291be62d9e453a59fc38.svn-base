package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @FileName : GoodsOptionMapper.java
 * @Project : datapick_api
 * @Date : 2021. 12. 1. 
 * @Author : KTH
 * ============================================================
 *  DATE               AUTHOR         NOTE
 * ============================================================
 *  2021. 12. 1.         KTH                 최초작성
 * ------------------------------------------------------------
 **/
@MapperInterface
public interface GoodsOptionMapper {

	/**
	 * 상품옵션 조회
	 * @param params
	 * @return SOMap
	 */
	List<SOMap> selectGoodsOptionList(SOMap params);
	
	/**
	 * 상품옵션(항목) 추가/수정
	 * @param params
	 * @return int
	 */
	int mergeGoodsOption(SOMap params);
	
	/**
	 * 상품옵션(항목) 삭제
	 * @param params
	 * @return int
	 */
	int updateGoodsOptionUse(SOMap params);

	/**
	 * ERP 전송 상품 옵션 조회
	 */
	List<SOMap> selectGoodsOptionListERP(SOMap params);
	
	
}
