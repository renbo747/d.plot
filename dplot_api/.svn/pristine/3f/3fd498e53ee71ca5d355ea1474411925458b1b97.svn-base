package com.dplot.admin.service.cs;

import java.util.Map;

import com.dplot.common.Response;
import com.dplot.common.SOMap;

/**
 * @author : ywm2004
 * @discription : CS관리 FAQ Service
 * @fileName : AdminCSFaqService.java
 * @date : 2021-11-10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-10	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
public interface AdminCSFaqService {

    /**
     * CS FAQ 조회
     *
     * @return
     */
    SOMap selectFaqList(SOMap params) throws Exception;

    /**
     * CS FAQ 상세 조회
     *
     * @return
     * @throws Exception
     */
    SOMap selectFaqDetail(SOMap params) throws Exception;

    /**
     * CS FAQ 저장
     */
    void insertFaq(SOMap params) throws Exception;

    /**
     * CS FAQ 수정
     *
     * @param params
     * @throws Exception
     */
    void updateFaq(SOMap params) throws Exception;

    /**
     * CS FAQ 메인노출 체크
     * @param params
     * @return
     * @throws Exception
     */
	SOMap checkFaqMain(SOMap params) throws Exception;

}
