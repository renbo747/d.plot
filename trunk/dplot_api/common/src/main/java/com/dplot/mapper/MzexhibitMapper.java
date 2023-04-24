package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : MzexhibitMapper.java
 * @Project : datapick_api
 * @Date : 2022. 03. 30. 
 * @Author : LKW
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 03. 30.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface MzexhibitMapper {

	/**
	 * 전시관리 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectMzexhibitList(SOMap params);

	/**
	 * 전시관리 전시여부별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectMzexhibitUseCnt(SOMap params);

	/**
	 * 전시관리 순서변경
	 * @param params
	 * @return
	 */
	int updateMzexhibitSortNum(SOMap params);

	/**
	 * 전시관리 수정
	 * @param params
	 * @return
	 */
	int updateMzexhibit(SOMap params);

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
	List<Map<String, Object>> selectMzexhibitExcelList(SOMap params);

	/**
	 * 전시관리 저장
	 * @param params
	 * @return
	 */
	int insertMzexhibit(SOMap params);

	/**
	 * 전시관리 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectMzexhibitDetail(SOMap params);
	
	/**
	 * 전시관리 전체 정렬순서 변경
	 * @param params
	 * @return
	 */
	int updateAllSortNum(SOMap params);

	/**
	 * Front 전시영역 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectFrontExhibitList(SOMap param);
	
	
}
