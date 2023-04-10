package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : ShopEx45Mapper.java
 * @Project : datapick_api
 * @Date : 2022. 04. 06. 
 * @Author : LKW
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 04. 06.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface ShopEx45Mapper {

	/**
	 * 전시영역45 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectShopEx45List(SOMap params);

	/**
	 * 전시영역45 전시여부별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectShopEx45Cnt(SOMap params);

	/**
	 * 전시영역45 추가
	 * @param params
	 * @return
	 */
	int insertShopEx45(SOMap params);

	/**
	 * 전시영역45 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectShopEx45ExcelList(SOMap params);

	/**
	 * 전시영역45 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectShopEx45Detail(SOMap params);

	/**
	 * 전시영역45 수정
	 * @param params
	 * @return
	 */
	int updateShopEx45(SOMap params);

	/**
	 * 전시영역45 노출조건 수량체크
	 * @param params
	 * @return
	 */
	SOMap selectDisplayCheckCnt(SOMap params);

	/**
	 * 전시영역45 리스트로 노출조건 수정 시 최대 수량체크
	 * @param params
	 * @return
	 */
	int selectDisplayCheckCntList(SOMap params);

	/**
	 * FRONT 전시 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectFrontShopExList(SOMap param);
	
	
}
