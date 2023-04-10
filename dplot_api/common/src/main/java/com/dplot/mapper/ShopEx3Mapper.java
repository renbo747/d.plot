package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : ShopEx3Mapper.java
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
public interface ShopEx3Mapper {

	/**
	 * 전시관리 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectShopExThreeList(SOMap params);

	/**
	 * 전시관리 전시여부별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectShopExThreeUseCnt(SOMap params);

	/**
	 * 전시관리 순서변경
	 * @param params
	 * @return
	 */
	int updateShopExThreeSortNum(SOMap params);

	/**
	 * 전시관리 수정
	 * @param params
	 * @return
	 */
	int updateShopExThree(SOMap params);

	/**
	 * 전시관리 조건수량 체크
	 * @param params
	 * @return
	 */
	int selectUseCount(SOMap params);

	/**
	 * 전시관리 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectShopExThreeExcelList(SOMap params);

	/**
	 * 전시관리 저장
	 * @param params
	 * @return
	 */
	int insertShopExThree(SOMap params);

	/**
	 * 전시관리 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectShopExThreeDetail(SOMap params);
	
	/**
	 * 전시관리 전체 정렬순서 변경
	 * @param params
	 * @return
	 */
	int updateAllSortNum(SOMap params);

	/**
	 * Front 전시 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectFrontShopExList(SOMap param);
	
	
}
