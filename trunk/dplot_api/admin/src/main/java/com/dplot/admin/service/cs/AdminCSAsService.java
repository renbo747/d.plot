package com.dplot.admin.service.cs;

import com.dplot.common.SOMap;

/**
 * @discription	: A/S문의 Service
 * @fileName	: AdminCSAsService.java
 * @author		: JSK
 * @date		: 2022.03.25
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.25	JSK			최초생성
 * -----------------------------------------------------------------
 */
public interface AdminCSAsService {

    /**
     * A/S문의 목록조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    SOMap selectAsInquiryList(SOMap params) throws Exception;

    /**
     * A/S문의 신청취소
     * @param params
     * @return SOMap
     * @throws Exception
     */
    int updateAsStatusCancel(SOMap params) throws Exception;

    /**
     * A/S문의 파트너사 전송
     * @param params
     * @return SOMap
     * @throws Exception
     */
    int sendPartnerAsInquiry(SOMap params) throws Exception;

    /**
     * A/S문의 상세조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    SOMap selectAsInquiryDetail(SOMap params) throws Exception;

    /**
     * A/S문의 답변
     * @param params
     * @return SOMap
     * @throws Exception
     */
    int saveAsInquiry(SOMap params) throws Exception;
}
