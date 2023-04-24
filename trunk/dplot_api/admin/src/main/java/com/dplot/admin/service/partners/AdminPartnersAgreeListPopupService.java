package com.dplot.admin.service.partners;

import java.util.Map;

/**
 * 설명 : 파트너사 미동의, 동의 목록 서비스
 * 생성 : 정재호
 * 일지 : 2021-11-08
 */
public interface AdminPartnersAgreeListPopupService {

    /**
     * 파트너사 미동의, 동의 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    Map<String, Object> selectAgreeList(Map<String, Object> params) throws Exception;
}
