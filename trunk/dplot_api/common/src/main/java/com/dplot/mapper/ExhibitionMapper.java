package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 기획전 Mapper (T_EXHIBITION)
 * @fileName	: ExhibitionMapper.java
 * @author		: LKW
 * @date		: 2021.11.26
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.26	LKW			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface ExhibitionMapper {
	
	/**
	 * Front 메인전시 기획전 조회
	 * @param dbparams
	 * @return
	 */
	SOMap selectFrontExhibition(SOMap dbparams);
	
	/**
	 * FRONT 메인전시 테마 기획전 조회
	 * @param dbparams
	 * @return
	 */
	List<SOMap> selectFrontExhibitGoodsList(SOMap dbparams);
	

	/**
	 * 기획전 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectExhibitList(SOMap params);

	/**
	 * 기획전 사용여부별 건수 조회
	 * @param params
	 * @return SOMap
	 */
	SOMap selectExhibitUseCount(SOMap params);

	/**
	 * 기획전 사용여부 수정
	 * @param params
	 * @return int
	 */
	int updateExhibitUse(SOMap params);

	/**
	 * 기획전 메인노출가능 체크
	 * @param params
	 * @return int
	 */
	int checkExhibitMain(SOMap params);

	/**
	 * 기획전 엑셀 다운로드
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectExhibitExcelList(SOMap params);

	
	/**
	 * 기획전 저장
	 * @param params
	 * @return
	 */
	int insertExhibit(SOMap params);

	/**
	 * 기획전 테마 저장
	 * @param info
	 * @return
	 */
	int insertExhibitTheme(Map<String, Object> info);

	/**
	 * 기획전 테마 상품 저장
	 * @param info
	 */
	void insertExhThemeGoods(Map<String, Object> info);
	
	/**
	 * 기획전 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectExhibitDetail(SOMap params);

	/**
	 * 기획전 상세 테마 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectExhibitTheme(SOMap params);

	/**
	 * 기획전 상세 테마상품 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectExhibitGoods(SOMap params);

	/**
	 * 기획전 조회수 증가
	 * @param params
	 * @return
	 */
	int updateExhibitReadCnt(SOMap params);

	/**
	 * 기획전 수정
	 * @param params
	 * @return
	 */
	int updateExhibit(SOMap params);

	/**
	 * 기획전 테마 수정
	 * @param info
	 * @return
	 */
	int updateExhibitTheme(Map<String, Object> info);

	/**
	 * 기획전 테마상품 삭제
	 * @param params
	 * @return
	 */
	int removeExhThemeGoods(SOMap params);

	/**
	 * 기획전 테마상품 수정
	 * @param params
	 * @return
	 */
	int updateExhThemeGoods(SOMap tempMap);
}
