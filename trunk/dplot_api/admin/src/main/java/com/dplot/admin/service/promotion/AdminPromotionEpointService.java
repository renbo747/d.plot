package com.dplot.admin.service.promotion;

import com.dplot.common.SOMap;

/**
 * @author : ywm2004
 * @discription : E포인트 관리 Service
 * @fileName : AdminPromotionEpointService.java
 * @date : 2022-01-04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-04	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
public interface AdminPromotionEpointService {

    /**
     * D포인트 저장
     *
     * @param params
     * @return
     * @throws Exception
     */
    void insertEpoint(SOMap params) throws Exception;

    /**
     * D포인트 수정
     *
     * @param params
     * @throws Exception
     */
    void updateEpoint(SOMap params) throws Exception;

    /**
     * D포인트 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectEpointList(SOMap params) throws Exception;

    /**
     * D포인트 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectEPointDetail(SOMap params) throws Exception;

    /**
     * 유저 정보 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectMemberInfo(SOMap params) throws Exception;

    /**
     * 적립/차감 내역 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectUsageDetailList(SOMap params) throws Exception;

}
