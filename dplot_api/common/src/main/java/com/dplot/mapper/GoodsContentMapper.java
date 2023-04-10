package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 상품내용 Mapper (T_GOODS_CONTENT)
 * @fileName	: GoodsContentMapper.java
 * @author		: JSK
 * @date		: 2021.12.01
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.01	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsContentMapper {
    
    /**
     * 상품내용 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
     */
	SOMap selectGoodsContent(SOMap param) throws Exception;
	
	/**
	 * 상품내용 저장
	 * @param params
	 * @return int
	 */
	int mergeGoodsContent(SOMap params);
	
	/**
	 * 상품내용 수정
	 * @param params
	 * @return int
	 */
	int updateAllGoodsContent(SOMap params);
}
