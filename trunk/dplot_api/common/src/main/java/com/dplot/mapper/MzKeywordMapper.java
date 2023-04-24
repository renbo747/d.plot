package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : MzKeywordMapper.java
 * @Project : datapick_api
 * @Date : 2022. 03. 31. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 03. 31.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface MzKeywordMapper {

	/**
	 * 매거진키워드 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectMzKeywordList(SOMap params);

	/**
	 * 매거진키워드 전시여부별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectMzKeywordCnt(SOMap params);

	/**
	 * 매거진키워드 추가
	 * @param params
	 * @return
	 */
	int insertMzKeyword(SOMap params);

	/**
	 * 매거진키워드 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectMzKeywordExcelList(SOMap params);

	/**
	 * 매거진키워드 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectMzKeywordDetail(SOMap params);

	/**
	 * 매거진키워드 수정
	 * @param params
	 * @return
	 */
	int updateMzKeyword(SOMap params);

	/**
	 * 매거진키워드 노출조건 수량체크
	 * @param params
	 * @return
	 */
	int selectDisplayCheckCnt(SOMap params);

	/**
	 * 매거진키워드 리스트로 노출조건 수정 시 최대 수량체크
	 * @param params
	 * @return
	 */
	int selectDisplayCheckCntList(SOMap params);

	/**
	 * FRONT 키워드 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectFrontKeywordList(SOMap param);
	
	
}
