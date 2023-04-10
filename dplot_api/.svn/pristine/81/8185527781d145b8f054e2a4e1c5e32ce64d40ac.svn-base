package com.dplot.admin.service.common;

import com.dplot.common.SOMap;

import java.util.Map;

public interface ExternalService {
    /**
     * 모두사인 계약요청
     * @param param
     * @return
     */
    SOMap partnersSignRequest(SOMap param) throws Exception;

    /**
     * 모두사인 상태 및 아이디 업데이트
     * @param param
     * @return
     */
    int partnersSignStateUpdate(SOMap param) throws Exception ;

    /**
     * 인스타그램 토큰 갱신 처리
     * @param code
     * @param serverName
     * @return
     * @throws Exception
     */
    int updateConfigInstaToken(String code, String serverName) throws Exception;

    /**
     * 모두사인 계약 URL 가져오기
     * @param moduId
     * @return
     */
    String getContractUrl(String moduId);
}
