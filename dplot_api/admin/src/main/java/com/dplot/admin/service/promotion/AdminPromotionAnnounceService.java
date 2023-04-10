package com.dplot.admin.service.promotion;

import com.dplot.common.SOMap;

/**
 * @author : ywm2004
 * @discription : 이벤트 당첨자 발표 Service
 * @fileName : AdminPromotionAnnounceService.java
 * @date : 2021-12-21
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-21	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
public interface AdminPromotionAnnounceService {

    /**
     * 이벤트 당첨자 발표 게시
     *
     * @param params
     * @throws Exception
     */
    void insertAnnounce(SOMap params) throws Exception;

    /**
     * 이벤트 당첨자 발표 수정
     *
     * @param params
     * @throws Exception
     */
    void updateAnnounce(SOMap params) throws Exception;

    /**
     * 종료 이벤트 리스트 조회
     *
     * @return
     * @throws Exception
     */
    SOMap selectEndEventList(SOMap params) throws Exception;

    /**
     * 이벤트 당첨자 발표 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectEventAnnounceList(SOMap params) throws Exception;

    /**
     * 이벤트 당첨자 발표 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectEventAnnounceDetail(SOMap params) throws Exception;

    /**
     * 응모 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectEnterList(SOMap params) throws Exception;

}
