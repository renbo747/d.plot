package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : ComOrderGoodsHisMapper.java
 * @Project : datapick_api
 * @Date : 2022. 1. 26. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 1. 26.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface ComOrderGoodsHisMapper {
	/**
	 * 주문상태이력정보 저장
	 * @param dbParam
	 * @return
	 */
	int insertComOrderGoodsHis(SOMap dbParam);
	
	/**
	 * 주문상태이력정보 일괄저장
	 * @param dbParam
	 * @return
	 */
	int insertComOrderGoodsHisAll(SOMap dbParam);
	
	/**
	 * 관리자용 주문상태 변경이력 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectOrderStatusHistList(SOMap params);
}
