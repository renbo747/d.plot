package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;


@MapperInterface
public interface DealerMemoMapper {
	/**
	 * 파트너사 메모 등록
	 * @param param
	 * @return
	 */
	int insertPartnersMemo(SOMap param);

	/**
	 * 파트너사 메모 수정
	 * @param param
	 * @return
	 */
	int updatePartnersMemo(SOMap param);

	/**
	 * 파트너사 메모 리스트 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectPartnsersMemo(SOMap param);

	/**
	 * 파트너사 메모 리스트 카운트
	 * @param param
	 * @return
	 */
	int selectPartnersMemoCount(SOMap param);
}
