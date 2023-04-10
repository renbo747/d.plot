package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 상품주문
 * @fileName	: OrderMapper.java
 * @author		: KTH
 * @date		: 2021.12.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.10	KTH			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface OrderMapper {

	/**
	 * 주문번호 조회
	 * @param params
	 * @return
	 */
	String selectOrderNo(SOMap params);
	
	/**
	 * 주문건수 조회
	 * @param param
	 * @return
	 */
	int selectOrderCnt(SOMap param);
	
	/**
	 * 주문/결제 목록 조회
	 *
	 * @param params the params
	 * @return the array list
	 */
	List<SOMap> selectOrderList(SOMap params);
	
	/**
	 * 주문/결제 체크목록 조회
	 *
	 * @param params the params
	 * @return the array list
	 */
	List<SOMap> selectCheckOrderList(SOMap params);
	
}
