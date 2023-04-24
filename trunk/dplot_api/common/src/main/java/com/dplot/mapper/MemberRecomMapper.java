package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface MemberRecomMapper {

	/**
	 * 회원 리워드 저장
	 * @param memberParam
	 * @return
	 */
	int insertMemberRecom(SOMap memberParam);

}
