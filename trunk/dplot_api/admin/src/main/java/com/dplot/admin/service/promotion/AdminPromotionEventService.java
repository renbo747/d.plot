package com.dplot.admin.service.promotion;

import com.dplot.common.SOMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 프로모션 이벤트 Service
 * @fileName : AdminPromotionEventService.java
 * @date : 2021-12-21
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-21	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
public interface AdminPromotionEventService {

    /**
     * 이벤트 등록
     *
     * @param params
     * @throws Exception
     */
    void insertEvent(SOMap params, Map<String, MultipartFile> files) throws Exception;

    /**
     * 이벤트 상세 수정
     *
     * @param params
     * @param files
     * @throws Exception
     */
    void updateEventDetail(SOMap params, Map<String, MultipartFile> files) throws Exception;

    /**
     * 이벤트 사용 여부 수정
     *
     * @param params
     * @throws Exception
     */
    void updateEventUse(SOMap params) throws Exception;

    /**
     * 이벤트 댓글 블라인드 수정
     *
     * @param params
     * @throws Exception
     */
    void updateEventCommentBlind(SOMap params) throws Exception;

    /**
     * 이벤트 댓글 추가, 수정
     *
     * @param params
     * @throws Exception
     */
    void insertAndUpdateEventComment(SOMap params) throws Exception;

    /**
     * 이벤트 댓글 삭제
     *
     * @param params
     * @throws Exception
     */
    void deleteEventComment(SOMap params) throws Exception;
//
//    /**
//     * 저장 가능 여부
//     *
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    SOMap isCanSaveCheck(SOMap params) throws Exception;

    /**
     * 이벤트 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectEventList(SOMap params) throws Exception;

    /**
     * 이벤트 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectEventDetail(SOMap params) throws Exception;

    /**
     * 이벤트 댓글 신고 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectEventReportList(SOMap params) throws Exception;

    /**
     * 이벤트 응모 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectEventEnterList(SOMap params) throws Exception;

    /**
     * 페이지에 필요한 코드값 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
//    SOMap selectCodeList(SOMap params) throws Exception;

    /**
     * 댓글 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectCommentList(SOMap params) throws Exception;

    /**
     * 엑셀 다운로드 - 이벤트 관리 댓글
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> selectEventCommentExcelDownload(SOMap params) throws Exception;

    /**
     * 엑셀 다운로드 - 이벤트 관리 응모
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> selectEventEnterExcelDownload(SOMap params) throws Exception;
}
