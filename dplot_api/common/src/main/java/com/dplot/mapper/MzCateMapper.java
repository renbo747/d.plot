package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : MzCateMapper.java
 * @Project : datapick_api
 * @Date : 2022. 04. 01. 
 * @Author : LKW
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 04. 01.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface MzCateMapper {

	/**
	 * 매거진카테고리 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectMzCategoryList(SOMap params);

	/**
	 * 매거진카테고리 전시여부별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectMzCategoryShowCnt(SOMap params);

	/**
	 * 매거진카테고리 추가
	 * @param params
	 * @return
	 */
	int insertMzCategory(SOMap params);

	/**
	 * 매거진카테고리 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectMzCategoryExcelList(SOMap params);

	/**
	 * 매거진카테고리 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectMzCategoryDetail(SOMap params);

	/**
	 * 매거진카테고리 수정
	 * @param params
	 * @return
	 */
	int updateMzCategory(SOMap params);

	/**
	 * 매거진카테고리 노출조건 수량체크
	 * @param params
	 * @return
	 */
	int checkDisplay(SOMap params);

	/**
	 * 매거진카테고리 전체목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectAllMzCateList(SOMap params);
	
}
