package com.dplot.admin.service.partners;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;

public interface AdminPartnersStatusService {
	/**
	 * 파트너사현황 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
    SOMap selectPartnersList(SOMap param) throws Exception;

    /**
     * 파트너사현황 상태 변경
     * @param param
     * @return
     * @throws Exception
     */
    int updatePartnersState(SOMap param) throws Exception;

    /**
     * 파트너사현황 메모 조회
     * @param param
     * @return
     * @throws Exception
     */
    SOMap selectPartnsersMemo(SOMap param) throws Exception;

    /**
     * 파트너사현황 메모 추가, 수정
     * @param params
     * @return
     * @throws Exception 
     */
	int insertOrUpdatePartnsersMemo(SOMap params) throws Exception;

	/**
	 * 엑셀 다운로드용 데이터 조회
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> selectPartnersExcelList(SOMap param) throws Exception;
}
