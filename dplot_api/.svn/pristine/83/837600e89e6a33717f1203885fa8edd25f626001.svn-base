package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 딜상품 Mapper (T_DEAL_GOODS)
 * @fileName	: DealGoodsMapper.java
 * @author		: JSK
 * @date		: 2021.12.06
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.06	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface DealGoodsMapper {
    
    /**
     * 상품별 구성상품목록 조회(엑셀다운로드용)
     * @param param
     * @return List<Map<String, Object>>
     * @throws Exception
     */
	List<Map<String, Object>> selectGoodsConstGoodsListForExcel(SOMap param) throws Exception;
	
    /**
     * 상품별 구성상품목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
     */
	List<SOMap> selectGoodsConstGoodsList(SOMap param) throws Exception;
	
	/**
	 * 상품별 구성상품 추가
	 * @param params
	 * @return int
	 */
	int insertConstGoods(SOMap params);
	
	/**
	 * 상품별 구성상품 삭제
	 * @param params
	 * @return int
	 */
	int deleteConstGoods(SOMap params);
}
