package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

/**
 * @author : ywm2004
 * @discription : 시리얼 프로모션 테이블 Mapper
 * @fileName : SerialPromo.java
 * @date : 2022-01-07
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-07	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface SerialPromoMapper {

    /**
     * 시리얼 프로모션 저장
     *
     * @param params
     */
    void insertSerialPromo(SOMap params);

    /**
     * 시리얼 프로모션 수정
     *
     * @param params
     */
    void updateSerialPromo(SOMap params);

    /**
     * 시리얼 프로모션 리스트 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectAdminSerialPromotionList(SOMap params);

    /**
     * 시리얼 프로모션 리스트 상세
     *
     * @param params
     * @return
     */
    SOMap selectAdminSerialPromotionDetail(SOMap params);

    /**
     * 시리얼 프로모션 리스트 상태 값 (전체)
     *
     * @param params
     * @return
     */
    SOMap selectAdminSerialPromotionState(SOMap params);

    /**
     * 진행전 상태 확인
     * 
     * @param params
     * @return
     */
    SOMap isAdminCanUpdateCheck(SOMap params);

    /**
     * 현재 날짜에 등록된 시리얼 프로모션 갯수
     * 
     * @return
     */
    int currentDateBySerialPromotionRegisterCount();

    /**
     * 시리얼프로모션 조회
     * @param param
     * @return
     */
	SOMap selectFrontSerialInfo(SOMap param);
}
