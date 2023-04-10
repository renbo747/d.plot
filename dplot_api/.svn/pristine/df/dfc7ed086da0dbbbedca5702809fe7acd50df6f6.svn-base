package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 다다이벤트 댓글 테이블 맵퍼 (t_dadaevent_comment)
 * @fileName : DadaEventComment.java
 * @date : 2021-12-15
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-15	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface DadaEventCommentMapper {

    /**
     * 이벤트 댓글 조회
     *
     * @param params
     * @return
     */
    // userno 컬럼 추가되면 v_memeber와 join해서 유저 정보 가져오기
    // 2021-12-15 Reguserid 사용으로 결정 (DB변경 요청 사항)
    List<SOMap> selectAdminDadaEventCommentList(SOMap params);

    /**
     * 이벤트 댓글 추가
     *
     * @param params
     */
    void insertDadaEventComment(SOMap params);

    /**
     * 이벤트 댓글 수정
     *
     * @param params
     */
    void updateDadaEventComment(SOMap params);

    /**
     * 이벤트 댓글 삭제
     *
     * @param params
     */
    void deleteDadaEventComment(SOMap params);

    /**
     * 엑셀 다운 쿼리
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> selectAdminCommentExcelDownload(SOMap params);
    
    /**FRONT 프로모션 댓글 추가
     * @param params
     * @return
     */
    int insertEventComment(SOMap params);
    
    /**FRONT 댓글 리스트 Depth 0
     * @param params
     * @return
     */
    List<SOMap> selectCommentListDep0(SOMap params);
    
    /**FRONT 댓글 리스트 Depth 0 카운트
     * @param params
     * @return
     */
    int selectCommentListDep0_Count(SOMap params);
    
    /**FRONT 댓글 리스트 Depth 1 리스트
     * @param params
     * @return
     */
    List<SOMap> selectCommentListDep1(SOMap params);
    
    /**FRONT 댓글 삭제 처리
     * @param param
     * @return
     */
    int deleteComment(SOMap param);
    
    /**FRONT 댓글 수정
     * @param param
     * @return
     */
    int updateComment(SOMap param);
    
    /**FRONT 댓글 신고
     * @param param
     * @return
     */
    int updateReport(SOMap param);
}
