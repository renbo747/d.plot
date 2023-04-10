package com.dplot.partners.service;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;

public interface PartnersCommonService {

    /**
     * 파트너사 사이드 메뉴
     * @param params
     * @return List<SOMap>
     * @throws Exception
     */
    List<SOMap> selectPartnersSubMenu(SOMap params) throws Exception;

    /**
     * 제휴담당자 정보 조회
     */
    SOMap getChargerInfoByType(SOMap params) throws Exception;

    /**
     * 정보 수정을 위한 본인 인증 문자 메세지 전송
     */
    SOMap sendAuthMessage(SOMap params) throws Exception;

    /**
     * 파트너사 매출 현황
     */
    SOMap selectPartnersSaleList(SOMap params) throws Exception;

    /**
     * 파트너사 매출 현황 엑셀
     */
    List<Map<String, Object>> selectPartnersSaleListExcel(SOMap params) throws Exception;

}
