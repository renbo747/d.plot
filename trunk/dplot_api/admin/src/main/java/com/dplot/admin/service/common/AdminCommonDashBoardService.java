package com.dplot.admin.service.common;

import com.dplot.common.SOMap;

public interface AdminCommonDashBoardService {

    /**
     * 관리자 메인 대시보드 데이터 조회
     */
    SOMap selectDashBoardData(SOMap param) throws Exception;

    /**
     * 파트너사 메인 대시보드 데이터 조회
     */
    SOMap selectPartnerDashBoardData(SOMap param) throws Exception;

}
