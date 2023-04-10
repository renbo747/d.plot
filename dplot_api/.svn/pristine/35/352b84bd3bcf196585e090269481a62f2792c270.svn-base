package com.dplot.mapper;


import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * The Interface ConfigTermMapper.
 */
@MapperInterface
public interface ConfigTermMapper {

	List<SOMap> selectConfigTerm(SOMap param);

	/**
	 * 이용약관 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectTermsList(SOMap params);

	/**
	 * 이용약관 조회
	 * @param params
	 * @return
	 */
	SOMap selectTerms(SOMap params);

	/**
	 * 이용약관 수정
	 * @param params
	 * @return
	 */
	int updateTerms(SOMap params);

	List<SOMap> selectPartnersTerms(SOMap param);

	/**
	 * 이용약관 수량
	 * @param params
	 * @return
	 */
	SOMap selectConfigTermCnt(SOMap params);
}
