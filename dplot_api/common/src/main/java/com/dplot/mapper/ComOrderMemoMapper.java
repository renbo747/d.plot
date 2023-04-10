package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 주문관리자메모 Mapper (T_COMORDER_MEMO)
 * @fileName	: ComOrderMemoMapper.java
 * @author		: JSK
 * @date		: 2022.02.22
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.02.22	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface ComOrderMemoMapper {

	/**
	 * 관리자메모 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectOrderMemoList(SOMap params);

	/**
	 * 관리자메모 추가
	 * @param params
	 * @return int
	 */
	int insertOrderMemo(SOMap params);

	/**
	 * 관리자메모 삭제여부 갱신
	 * @param params
	 * @return int
	 */
	int updateOrderMemoUse(SOMap params);
}
