package com.dplot.mapper;


import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;


@MapperInterface
public interface NewGoodsMapper {

	/**
	 * Front 메인전시 신상품목록조회
	 * @param dbparams
	 * @return
	 */
	List<SOMap> selectFrontNewGoodsList(SOMap dbparams);

	/**
	 * 신상품 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectNewGoodsList(SOMap params);
	
	/**
	 * 신상품 사용여부별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectNewGoodsUseCount(SOMap params);

	/**
	 * 신상품 상태 수정
	 * @param params
	 * @return
	 */
	int updateNewGoodsUse(SOMap params);

	/**
	 * 신상품 추가(중복상품 제외)
	 * @param params
	 * @return
	 */
	int insertNewGoods(SOMap params);

	/**
	 * 중복 신상품 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectDulpNewGoods(SOMap params);

	/**
	 * 신상품 순서변경
	 * @param params
	 * @return
	 */
	int updateNewGoodsSort(SOMap params);

	/**
	 * 신상품 엑셀 다운로드 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectNewGoodsExcelList(SOMap params);
}
