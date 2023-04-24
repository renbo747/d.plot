package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 리뷰묶어보기 Mapper (T_GOODS_GRP)
 * @fileName	: GoodsGrpMapper.java
 * @author		: JSK
 * @date		: 2021.11.29
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.29	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsGrpMapper {
    
    /**
     * 리뷰묶어보기 목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
     */
    List<SOMap> selectGoodsGrpList(SOMap param) throws Exception;
	
	/**
	 * 리뷰묶어보기 상품 추가
	 * @param params
	 * @return int
	 */
	int insertGoodsGrp(SOMap params);
	
	/**
	 * 리뷰묶어보기 상품 삭제
	 * @param params
	 * @return int
	 */
	int deleteGoodsGrp(SOMap params);
}
