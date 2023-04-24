package com.dplot.admin.service.cs;

import com.dplot.common.SOMap;

/**
 * @discription	: 답변템플릿 Service
 * @fileName	: AdminCSRepTemplateService.java
 * @author		: JSK
 * @date		: 2022.03.30
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.30	JSK			최초생성
 * -----------------------------------------------------------------
 */
public interface AdminCSRepTemplateService {

    /**
     * 답변템플릿 목록조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    SOMap selectRepTemplateList(SOMap params) throws Exception;

    /**
     * 답변템플릿 상세조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    SOMap selectRepTemplateDetail(SOMap params) throws Exception;

    /**
     * 답변템플릿 저장
     * @param params
     * @return SOMap
     * @throws Exception
     */
    int mergeRepTemplate(SOMap params) throws Exception;

    /**
     * 답변템플릿 삭제
     * @param params
     * @return SOMap
     * @throws Exception
     */
    int removeRepTemplate(SOMap params) throws Exception;

    /**
     * 답변템플릿 노출순서 변경
     * @param params
     * @return SOMap
     * @throws Exception
     */
    int updateRepTemplateSort(SOMap params) throws Exception;
}
