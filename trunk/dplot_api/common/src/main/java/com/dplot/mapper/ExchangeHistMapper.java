package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface ExchangeHistMapper {
	
	/**
	 * 교환이력 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectExchangeList(SOMap params);
	
	/**
	 * 교환이력 저장
	 * @param params
	 * @return
	 */
	int insertExchangeHist(SOMap params);
}
