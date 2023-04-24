package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface SerialMemberMapper {

	/**
	 * 시리얼회원발급 추가
	 * @param param
	 * @return
	 */
	int insertSerialMember(SOMap param);

}
