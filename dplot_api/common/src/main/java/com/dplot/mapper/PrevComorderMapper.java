package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface PrevComorderMapper {

	/**
	 * 이전주문내역조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectPrevComorder(SOMap param);

}
