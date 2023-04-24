package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @author : ywm2004
 * @discription : E포인트지급내역 Mapper
 * @fileName : EpointPayMapper.java
 * @date : 2022-01-03
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-03	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface EpointPayMapper {

    /**
     * E포인트 지급 내역 저장
     *
     * @param params
     * @return int
     */
    int insertEpointPay(SOMap params);

    /**
     * E포인트 지급 내역 수정
     * 
     * @param params
     */
    void updateEpointPay(SOMap params);

    /**
     * E포인트 지급 내역 삭제
     * 
     * @param params
     */
    void deleteEpointPay(SOMap params);

    /**
     * E포인트 적립/차감/소멸 내역 조회
     *
     * @param params
     * @return
     * @deprecated union 삭제로 적립, 차감/소멸 내역을 각각 가져옵니다.
     */
    List<SOMap> selectAdminEpointPayUsageDetailList(SOMap params);

    /**
     * e포인트 적립 idx 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectAdminEpointPayList(SOMap params);

    /**
     * E포인트 회원 보유 내역 조회
     * @param params
     * @return
     */
    SOMap getMemberEpointInfo(SOMap params);

    /**
     * 회원 e포인트 리스트
     * @param params
     * @return
     */
    List<SOMap> selectMemberEPointList(SOMap params);

    /**
     * 회원 e포인트 리스트 카운트
     * @param params
     * @return
     */
    SOMap selectMemberEPointCountInfo(SOMap params);

    /**
     * 회원 e포인트 리스트 엑셀
     * @param params
     * @return
     */
    List<Map<String, Object>> selectMemberEPointExcelList(SOMap params);

    /**
     * 회원 e포인트 목록 조회
     * @param param
     * @return
     */
	List<SOMap> selectFrontEpointList(SOMap param);

	/**
	 * 사용자별 일주일 이내 소멸금액조회
	 * @param param
	 * @return
	 */
	SOMap selectEpointInfo(SOMap param);

	/**
	 * E포인트지급내역IDX 목록 조회
	 * @param params
	 * @return List<Integer>
	 */
	List<Integer> selectEpopayidxListByIdx(SOMap params);
	
	/**
	 * E포인트 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectEpointPayListByUser(SOMap params);
	
	/**
	 * E포인트 기상용포인트 수정
	 * @param params
	 * @return
	 */
	int updateEpointPayUsedPoint(SOMap params);
}
