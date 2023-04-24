package com.dplot.mapper;


import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * The Interface ConfigTermHistoryMapper.
 */
@MapperInterface
public interface ConfigTermHistoryMapper {

	/**
	 * 이용약관이력 추가
	 * @param params
	 * @return
	 */
	int insertTermsHistory(SOMap params);

	/**
	 * 이용약관이력 수정(현재버전 F로)
	 * @param params
	 * @return
	 */
	int updateTermsHistory(SOMap params);

	/**
	 * 이용약관 이력조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectTermsHistoryList(SOMap param);
}
