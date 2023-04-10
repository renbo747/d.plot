package com.dplot.admin.service.promotion;

import com.dplot.common.SOMap;

/**
 * @author : ywm2004
 * @discription : 시리얼 프로모션 Service
 * @fileName : AdminSerialPromotionService.java
 * @date : 2022-01-07
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-07	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
public interface AdminSerialPromotionService {

    /**
     * 시리얼 프로모션 저장
     *
     * @param params
     * @return
     */
    void insertSerialPromotion(SOMap params) throws Exception;

    /**
     * 시리얼 프로모션 수정
     *
     * @param params
     * @throws Exception
     */
    void updateSerialPromotion(SOMap params) throws Exception;

    /**
     * 시리얼 프로모션 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectSerialPromotionList(SOMap params) throws Exception;

    /**
     * 시리얼 프로모션 상세
     *
     * @param params
     * @return
     */
    SOMap selectSerialPromotionDetail(SOMap params);

    /**
     * S/N 등록 내역 조회
     *
     * @param params
     * @return
     */
    SOMap selectSerialNoList(SOMap params);

    /**
     * 시리얼 번호 중복 체크
     *
     * @param params
     * @return
     */
    SOMap isAdminCheckDuplicationSerialNo(SOMap params) throws Exception;

    /**
     * 시리얼 번호 중복 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectDuplicationSerialNoList(SOMap params) throws Exception;

//    /**
//     * 시리얼 프로모션 등록 가능 여부 판별
//     *
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    SOMap isAdminCanSaveCheck(SOMap params) throws Exception;

    /**
     * 시리얼 번호 엑셀 다운로드
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap serialNoExcelDownload(SOMap params) throws Exception;

}
