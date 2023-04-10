package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 상품메모 Mapper (T_GOODS_MEMO)
 * @fileName	: GoodsMemoMapper.java
 * @author		: JSK
 * @date		: 2021.11.23
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.23	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsMemoMapper {
    
    /**
     * 상품별메모 목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
     */
    List<SOMap> selectGoodsMemoList(SOMap param) throws Exception;
	
	/**
	 * 상품별메모 저장
	 * @param params
	 * @return int
	 */
	int mergeGoodsMemo(SOMap params);
}
