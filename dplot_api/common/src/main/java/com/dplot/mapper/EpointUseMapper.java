package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

/**
 * @discription    : E포인트 사용내역 Mapper
 * @fileName    : EpointUseMapper.java
 * @author        : ywm2004
 * @date        : 2022-02-22
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-02-22	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface EpointUseMapper {
	
	/**
	 * E포인트 사용 조회
	 * @param params
	 * @return
	 */
	SOMap selectEpointUseByOrder(SOMap params);
	
    /**
     * E포인트 사용 내역 조회
     * 
     * @param params
     * @return
     */
    List<SOMap> selectAdminEpointUseList(SOMap params);
    
    /**
	 * E포인트사용내역 저장
     * @param params
     * @return int
	 */
	int insertEpointUse(SOMap params);
	
	/**
	 * E포인트사용지급 저장
     * @param params
     * @return int
	 */
	int insertEpointUsepay(SOMap params);
}
