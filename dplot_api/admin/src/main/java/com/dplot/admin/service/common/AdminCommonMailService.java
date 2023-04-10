package com.dplot.admin.service.common;

import com.dplot.common.SOMap;

/**
 * @author : ywm2004
 * @discription : 메일 보내기 공통 팝업 Service
 * @fileName : AdminCommonMailService.java
 * @date : 2022-02-09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-02-09	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
public interface AdminCommonMailService {

    /**
     * 유저 정보 가져오기
     */
    SOMap selectUserInfo(SOMap params) throws Exception;

    /**
     * 메일 보내기
     */
    SOMap sendMail(SOMap params) throws Exception;
}
