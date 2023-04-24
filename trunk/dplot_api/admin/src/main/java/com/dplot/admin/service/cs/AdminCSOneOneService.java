package com.dplot.admin.service.cs;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @author : ywm2004
 * @discription : CS관리 1:1 문의 Service
 * @fileName : AdminCSOneOneService.java
 * @date : 2021-11-19
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-19	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
public interface AdminCSOneOneService {

    /**
     * 1:1 문의 조회
     *
     * @param params
     * @return
     */
    SOMap selectInquiryList(SOMap params) throws Exception;

    /**
     * 1:1 문의 상세
     *
     * @param params
     * @return
     */
    SOMap selectInquiryDtl(SOMap params) throws Exception;

    /**
     * 1:1 문의 수정
     *
     * @param params
     */
    void updateInquiry(SOMap params) throws Exception;

    /**
     * 문의 파트너사 전송
     * @param params
     * @return
     * @throws Exception
     */
	SOMap sendToPartnerInquiry(SOMap params) throws Exception;

}
