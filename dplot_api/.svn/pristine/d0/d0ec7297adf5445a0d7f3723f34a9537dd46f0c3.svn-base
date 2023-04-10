package com.dplot.admin.service.partners;

import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

public interface AdminPartnersApplyService {
    /**
     *  파트너사 목록 조회
     */
    SOMap selectPartnersList(SOMap param) throws Exception;

    /**
     *  파트너사 상태 업데이트(승인/반려등)
     */
    int updatePartnersState(SOMap param) throws Exception;

    /**
     *  파트너사 상세 조회
     */
    SOMap selectPartnsersDetail(SOMap param) throws Exception;

    /**
     *  파트너사 담당자 리스트 조회
     */
    List<SOMap> selectDealerCharge(SOMap param);

    /**
     *  파트너사 패스워드 초기화
     */
    SOMap initPassword(SOMap param);

    /**
     * 파트너사 담당자 삭제 및 갱신된 파트너사 담당자 리스트 리턴
     */
    SOMap deletePartnersCharger(SOMap param) throws Exception;
}
