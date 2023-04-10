package com.dplot.admin.service.partners;

import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 파트너사 선택 리스트 팝업 Service
 * @fileName : AdminPartnersListPopupService.java
 * @date : 2021-11-04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-04	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
public interface AdminPartnersListPopupService {

    /**
     * 전체 파트너사 리스트 조회
     *
     * @return
     * @throws Exception
     */
    Map<String, Object> selectPartnersList(Map<String, Object> params) throws Exception;


    /**
     * boardPostIdx로 선택 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    Map<String, Object> selectCheckList(Map<String, Object> params) throws Exception;

}
