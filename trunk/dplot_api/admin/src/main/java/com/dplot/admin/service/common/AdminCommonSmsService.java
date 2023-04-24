package com.dplot.admin.service.common;

import com.dplot.common.SOMap;

public interface AdminCommonSmsService {

    /**
     * 유저 정보 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectUserInfo(SOMap params) throws Exception;

    /**
     * 메세지 보내기
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap sendMessage(SOMap params) throws Exception;
}
