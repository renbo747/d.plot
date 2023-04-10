package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 추가구성상품 Mapper (T_GOODS_ADDITION)
 * @fileName	: GoodsAdditionMapper.java
 * @author		: JSK
 * @date		: 2021.11.26
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.26	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsAdditionMapper {
    
    /**
     * 추가상품목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
     */
    List<SOMap> selectGoodsAdditionList(SOMap param) throws Exception;
	
	/**
	 * 추가상품 추가
	 * @param params
	 * @return int
	 */
	int insertGoodsAddition(SOMap params);
	
	/**
	 * 추가상품 삭제
	 * @param params
	 * @return int
	 */
	int deleteGoodsAddition(SOMap params);
}
