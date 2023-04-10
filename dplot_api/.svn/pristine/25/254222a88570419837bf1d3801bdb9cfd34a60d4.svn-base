package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 다다이벤트 응모 테이블 맵퍼 (t_dadaevent_enter)
 * @fileName : DadaEventEnter.java
 * @date : 2021-12-15
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-15	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface DadaEventEnterMapper {

    /**
     * 이벤트 응모 리스트 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectAdminDadaEventEnterList(SOMap params);

    /**
     * 이벤트 응모 팝업 리스트 조회
     *
     * @param params
     * @return 응모 날짜만 가져옴
     */
    List<SOMap> selectAdminDadaEventEnterListPopup(SOMap params);

    /**
     * 이벤트 응모 유저 정보 포함 리스트 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectAdminDadaEventEnterByUserInfoList(SOMap params);

    /**
     * 이벤트 관리 엑셀 다운로드 - 응모
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> selectAdminEnterExcelDownload(SOMap params);

    /**
     * 이벤트 응모 저장 - 복수
     *
     * @param params
     */
    void insertAdminEventEnterArray(SOMap params);

    /**
     * 이벤트 응모 수정 - 복수
     *
     * @param params
     */
    void updateAdminEventEnterArray(SOMap params);

    /**
     * 이벤트 응모 저장 - 단수
     *
     * @param params
     */
	void insertAdminEventEnter(SOMap param);

    /**
     * 임직원은 이벤트 응모 제외
     *
     * @param params
     */
	List<SOMap> selectAdminDadaEventDMT003(SOMap param);

}
