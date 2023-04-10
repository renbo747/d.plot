package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

/**
 * @author : ywm2004
 * @discription : 이벤트 댓글 신고 Mapper (t_evtcomment_report)
 * @fileName : EvtCommentReportMapper.java
 * @date : 2021-12-21
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-12-21	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface EvtCommentReportMapper {

    /**
     * 이벤트 댓글 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectAdminEvtCommentReportList(SOMap params);

    /**
     * 이벤트 댓글 신고 추가
     * @param param
     * @return
     */
	int insertReportComment(SOMap param);
	
	/**
	 * 이벤트 댓글 신고여부 조회
	 * @param param
	 * @return
	 */
	int selectReportCommentCnt(SOMap param);
}
