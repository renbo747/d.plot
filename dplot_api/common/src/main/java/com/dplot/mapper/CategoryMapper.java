package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

/**
 * @discription    : 카테고리 Mapper (T_CATEGORY)
 * @fileName    : CategoryMapper.java
 * @author        : JSK
 * @date        : 2021.11.09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.09	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface CategoryMapper {

    /**
     * 카테고리 목록 조회
     *
     * @param params
     * @return List<SOMap>
     */
    List<SOMap> selectCategoryList(SOMap params);

    /**
     * 카테고리 목록 1DEPTH 조회
     *
     * @param param
     * @return
     */
    List<SOMap> selectProdTypeList(SOMap param);

    /**
     * 카테고리명 목록 조회
     *
     * @param params
     * @return List<SOMap>
     */
    List<SOMap> selectCategorynameList(SOMap params);

    /**
     * 카테고리 상세조회(idx)
     *
     * @param params
     * @return
     */
    SOMap selectCategoryDetail(SOMap params);

    /**
     * 상품상세 카테고리 브래드크럼 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectCategoryBreadCrumb(SOMap params);

    /**
     * 카테고리 트리구조 변경 저장
     *
     * @param params
     */
    void updateCategoryTree(SOMap params);

    /**
     * 카테고리 추가
     *
     * @param params
     */
    void insertCategory(SOMap params);

    /**
     * 카테고리 수정
     *
     * @param params
     */
    int updateCategory(SOMap params);

    /**
     * idx 기준을 하위 노드가 있는지, 해당 카테고리 상품이 있는지 검사
     *
     * @param params
     * @return 있으면 true, 없으면 false
     */
    boolean isSubNodesAndGoodsExists(SOMap params);

    List<SOMap> selectCategoryListERP(SOMap params);

    /**
     * 카테고리 정보 (BY idx)  조회*
     * @param param
     * @return
     */
	SOMap selectCategoryByIdx(SOMap param);

	List<SOMap> selectFrontCategoryList(SOMap params);

	/**
	 * 검색 카테고리 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectSearchCateList(SOMap param);

	/**
	 * 상품목록 현재카테고리 정보 조회
	 * @param param
	 * @return
	 */
	SOMap selectGoodsListCateInfo(SOMap param);

    /**
     * 상품 일괄업로드 카테고리 목록 조회
     *
     * @param params
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> selectCategorynameListForExcel(SOMap params);
    
    /**
     * 카테고리 카운트 조회(유효성체크)
     * @param params
     * @return int
     */
    int selectCategoryCount(SOMap params);

    /**
     * 카테고리 정렬순서 변경(insert, update시)
     * @param params
     * @return
     */
	int updateCategorySort(SOMap params);
	
	/**
	 * PC 메인에 노출되는 카테고리 리스트 가져오기
	 * @param params
	 * @return
	 */
	List<SOMap> selectPcCategoryList(SOMap params);
}
