package com.dplot.admin.service.partners;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * 설명 : 파트너사 동의현황 게시판 서비스
 * 생성 : 정재호
 * 일지 : 2021-11-04
 */
public interface AdminPartnersConsentService {

    /**
     * 파트너사 동의 현황 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectConsentList(SOMap params) throws Exception;

    /**
     * 파트너사 동의 현황 상세 조회
     *
     * @param params
     * @return
     */
    SOMap selectConsentDetail(SOMap params) throws Exception;

    /**
     * 파트너사 동의 현황 수정
     *
     * @param params
     * @throws Exception
     */
    void updateConsent(SOMap params) throws Exception;

    /**
     * 파트너사 동의 현황 중지, 종료 변경
     *
     * @param params
     * @throws Exception
     */
    void updateTrash(SOMap params) throws Exception;

    /**
     * 파트너사 동의 현황 추가
     *ㄷ
     * @param params
     * @throws Exception
     */
    void insertConsent(SOMap params) throws Exception;

    /**
     * 파트너사 동의/미동의 수정
     * @param params
     * @return
     * @throws Exception
     */
	SOMap updateAgree(SOMap params) throws Exception;
}
