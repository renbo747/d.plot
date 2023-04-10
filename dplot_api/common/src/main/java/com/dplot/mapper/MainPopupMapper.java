package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : MainPopupMapper.java
 * @Project : datapick_api
 * @Date : 2022. 04. 11. 
 * @Author : LKW
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 04. 11.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface MainPopupMapper {
	/**
	 * 메인팝업 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectMainPopupList(SOMap params);

	/**
	 * 메인팝업 사용여부 별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectMainPopupListCnt(SOMap params);

	/**
	 * 메인팝업 저장
	 * @param params
	 * @return
	 */
	int insertMainPopup(SOMap params);

	/**
	 * 메인팝업 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectMainPopupDetail(SOMap params);

	/**
	 * 팝업 수정
	 * @param params
	 * @return
	 */
	int updateMainPopup(SOMap params);

	/**
	 * 메인팝업 기간에 따른 노출 수량 체크
	 * @param params
	 * @return
	 */
	int checkMainPopupCnt(SOMap params);
	
	/**
	 * 메인팝업 정렬순서 변경
	 * @param params
	 * @return
	 */
	int updateMainPopupSortNum(SOMap params);
	
	/**
	 * 팝업 전체 정렬순서 변경
	 * @param params
	 * @return
	 */
	int updateAllSortNum(SOMap params);

	/**
	 * 메인팝업 엑셀목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectMainPopupExcelList(SOMap params);

	/**
	 * FRONT 메인팝업 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectFrontMainPopupList(SOMap param);
}
