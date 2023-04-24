package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 적립금지급설정 Mapper (T_RESERVE_CONFIG)
 * @fileName	: ReserveConfigMapper.java
 * @author		: JSK
 * @date		: 2021.12.21
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.21	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface ReserveConfigMapper {
	
	/**
	 * 적립금 자동지급설정 조회
	 * 
     * @param param
     * @return List<SOMap>
     * @throws Exception
	 */
	List<SOMap> selectReserveConfig(SOMap params);
	
	/**
	 * 회원 유형으로 적립금지급 설정 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectReserveConfigByMemberType(SOMap param);

	
	/**
	 * 적립금 자동지급설정 저장
	 * 
     * @param param
     * @return int
     * @throws Exception
	 */
	int mergeReserveConfig(SOMap params);

	/**
	 * 회원 유형으로 적립금지급 설정 조회
	 * @param param
	 * @return
	 */
	SOMap selectReserveConfigInfoByMemberType(SOMap param);
	
	/**
	 * 회원 유형으로 상품별 프로모션 적립금지급 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectPromReserveByMemberType(SOMap param);
}
