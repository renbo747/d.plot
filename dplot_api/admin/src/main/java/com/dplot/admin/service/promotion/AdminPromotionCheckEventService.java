package com.dplot.admin.service.promotion;

import com.dplot.common.SOMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 프로모션 출석체크 이벤트 Service
 * @fileName : AdminPromotionCheckEventService.java
 * @date : 2021-12-22
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-22	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
public interface AdminPromotionCheckEventService {

    /***
     * 프로모션 출석체크 이벤트 저장
     *
     * @param params
     * @param files
     * @throws Exception
     */
    void insertCheckEvent(SOMap params, Map<String, MultipartFile> files) throws Exception;

    /**
     * 프로모션 출석체크 이벤트 수정
     * 
     * @param params
     * @param files
     * @throws Exception
     */
    void updateCheckEvent(SOMap params, Map<String, MultipartFile> files) throws Exception;

    /**
     * 프로모션 출석체크 이벤트 상세 조회
     *
     * @param params
     * @return
     */
    SOMap selectCheckEventDetail(SOMap params) throws Exception;

    /**
     * 출석 정보 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectAttendDetail(SOMap params) throws Exception;

    /**
     * 페이지에 필요한 코드 조회
     *
     * @return
     */
    SOMap selectCodeList() throws Exception;

    /**
     * 엑셀 다운로드
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> selectCheckAttendExcelDownload(SOMap params) throws Exception;
}
