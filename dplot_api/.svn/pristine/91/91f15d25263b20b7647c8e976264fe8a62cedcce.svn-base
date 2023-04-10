package com.dplot.admin.service.partners;

import com.dplot.common.SOMap;

import java.util.Map;

/**
 * 설명 : 파트너사 공지사항 게시판 서비스
 * 생성 : 정재호
 * 일지 : 2021-11-04
 */
public interface AdminPartnersNoticeService {

    /**
     * 파트너사 공지사항 상세 조회
     *
     * @param params
     * @return
     */
    SOMap selectNoticeDetail(SOMap params) throws Exception;

    /**
     * 파트너사 공지사항 변경
     *
     * @param params
     * @throws Exception
     */
    void updateBoardPost(SOMap params) throws Exception;

    /**
     * 파트너사 공지사항 사용 여부 변경
     *
     * @param params
     * @throws Exception
     */
    void updateBoardPostIsTrash(SOMap params) throws Exception;

    /**
     * 파트너사 공지사항 가져오기
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectNoticeList(SOMap params) throws Exception;

    /**
     * 파트너사 공지사항 저장
     *
     * @param params
     * @throws Exception
     */
    void insertNotice(SOMap params) throws Exception;

}
