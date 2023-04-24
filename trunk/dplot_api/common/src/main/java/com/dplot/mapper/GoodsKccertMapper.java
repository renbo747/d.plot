package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 상품KC인증 Mapper (T_GOODS_KCCERT)
 * @fileName	: GoodsKccertMapper.java
 * @author		: JSK
 * @date		: 2021.11.26
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.26	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsKccertMapper {
	
	/**
	 * 상품KC인증 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectGoodsKccertList(SOMap params);
	
	/**
	 * 상품KC인증 추가
	 * @param params
	 * @return int
	 */
	int insertGoodsKccert(SOMap params);
	
	/**
	 * 상품KC인증 삭제
	 * @param params
	 * @return int
	 */
	int deleteGoodsKccert(SOMap params);

	/**
	 * ERP 전송 상품 KC 인증정보 조회
	 */
	List<SOMap> selectGoodsKccertListERP(SOMap params);
}
