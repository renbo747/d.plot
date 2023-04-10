package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 상품승인이력 Mapper (T_GOODS_APPR)
 * @fileName	: GoodsApprMapper.java
 * @author		: JSK
 * @date		: 2021.12.16
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.16	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsApprMapper {

	/**
	 * 상품 승인상태 히스토리 저장
	 * @param params
	 * @return int
	 */
	int insertGoodsApprHist(SOMap params);
    
    /**
     * 상품승인이력 목록 조회
     * @param params
     * @return List<SOMap>
     * @throws Exception
     */
    List<SOMap> selectGoodsApprHistList(SOMap params) throws Exception;
}
