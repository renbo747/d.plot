package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;


/**
 * @discription	: 상품엑셀업로드Map Mapper
 * @fileName	: GoodsExcelMapMapper.java
 * @author		: JSK
 * @date		: 2022.07.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.07.10	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsExcelMapMapper {

	/**
	 * 상품엑셀업로드Map 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectGoodsExcelMapList(SOMap params);

	/**
	 * 상품엑셀업로드Map 저장
	 * @param params
	 * @return int
	 */
	int insertGoodsExcelMap(SOMap params);
}
