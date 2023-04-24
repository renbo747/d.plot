package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 적립금사용내역 Mapper (T_RESERVE_USE)
 * @fileName	: ReserveUserMapper.java
 * @author		: JSK
 * @date		: 2022.01.18
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.01.18	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface ReserveUseMapper {
	
	/**
	 * 적립금사용내역 조회(주문번호)
	 * @param params
	 * @return
	 */
	List<SOMap> selectReserveUseByOrder(SOMap params);
	
	/**
	 * 적립금사용내역 저장
     * @param params
     * @return int
	 */
	int insertReserveUse(SOMap params);
	
	/**
	 * 적립금사용지급 저장
     * @param params
     * @return int
	 */
	int insertReserveUsepay(SOMap params);
}
