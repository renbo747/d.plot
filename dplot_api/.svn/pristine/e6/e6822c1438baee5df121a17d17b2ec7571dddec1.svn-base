package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 브랜드 Mapper (T_BRAND)
 * @fileName	: BrandMapper.java
 * @author		: JSK
 * @date		: 2021.11.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.10	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface BrandMapper {

	/**
	 * 브랜드 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectBrandList(SOMap params);

	/**
	 * 브랜드 전체건수 조회
	 * @param params
	 * @return int
	 */
	int selectBrandListCount(SOMap params);
	
	/**
	 * 브랜드 사용여부별 건수 조회
	 * @param params
	 * @return
	 */
	SOMap selectBrandUseCount(SOMap params);
	
	/**
	 * FRONT 브랜드리스트 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectBrandFrontList(Map<String, Object> params);
	
	/**
	 * FRONT 프리미엄 브랜드리스트 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectPreBrandFrontList(SOMap params);

	/**
	 * 브랜드 추가
	 * @param params
	 * @return
	 */
	int insertBrand(SOMap params);
	
	/**
	 * 브랜드 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectBrandDetail(SOMap params);

	/**
	 * 브랜드 수정
	 * @param params
	 * @return
	 */
	int updateBrand(SOMap params);

	/**
	 * FRONT 브랜드 상세 조회
	 * @param param
	 * @return
	 */
	SOMap selectFrontBrandDetail(SOMap param);

	/**
	 * ERP에서 전달받은 브랜드 등록
	 */
	int insertBrandERPData(SOMap param);

	/**
	 * ERP에서 전달받은 브랜드 수정
	 */
	int updateBrandERPData(SOMap param);

	/**
	 * BRCODE 조회
	 */
	List<String> selectBrandCodeList(SOMap param);

	/**
	 * 상품 일괄업로드 브랜드 목록 조회
	 * @param params
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> selectBrandListForExcel(SOMap params);

	/**
	 * 검색 필터 브랜드 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectSearchBrand(SOMap param);
}
