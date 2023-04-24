package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;


/**
 * @discription	: 적립금지급내역 Mapper (T_RESERVE_PAY)
 * @fileName	: ReservePayMapper.java
 * @author		: JSK
 * @date		: 2021.12.20
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.20	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface ReservePayMapper {
	
	/**
	 * 적립금지급내역 저장
     * @param param
     * @return int
     * @throws Exception
	 */
	int insertReservePayAll(SOMap params);

	/**
	 * 회원정보 상세 - 회원 적립금 조회
	 * @param params
	 * @return
	 */
	SOMap getMemberCMoneyInfo(SOMap params);

	/**
	 * 적립금지급내역IDX 목록 조회
	 * @param params
	 * @return List<Integer>
	 */
	List<Integer> selectRespayidxListByIdx(SOMap params);

	/**
	 * 적립금/임직원적립금/EPOINT ERP 전송 데이터 조회 3개
	 */
	List<SOMap> selectReserveEPointERPData(SOMap params);
	List<SOMap> selectEPointUseERPData(SOMap params);
	List<SOMap> selectReserveUseERPData(SOMap params);
	
	/**
	 * 사용자적립금 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectReservePayListByUser(SOMap params);
	
	/**
	 * 사용자적립금 기상용포인트 수정
	 * @param params
	 * @return
	 */
	int updateReservePayUsedPoint(SOMap params);

	/**
	 * 회원 적립금 리스트
	 * @param params
	 * @return
	 */
	List<SOMap> selectMemberReserveList(SOMap params);

	/**
	 * 회원 적립금 리스트 카운트
	 * @param params
	 * @return
	 */
	SOMap selectMemberReserveCountInfo(SOMap params);
}
