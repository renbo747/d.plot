package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface MemberMsgMapper {
    int insertMemberMsg(SOMap param);

    /**
     * 메시지목록 조회
     * @param param
     * @return
     */
	List<SOMap> selectMyMsgList(SOMap param);

	/**
	 * 메시지목록 삭제
	 * @param param
	 * @return
	 */
	int updateMyMsgDel(SOMap param);

	 /**
     * 메시지목록수 조회
     * @param param
     * @return
     */
	int selectMyMsgListCnt(SOMap param);

	/**
	 * 메시지 읽음 처리
	 * @param param
	 * @return
	 */
	int updateMyMsgRead(SOMap param);
	
	/**
	 * 알림톡 템플릿 리스트
	 * 
	 * */
	SOMap selectMsgTemplate(SOMap param);
	/**
	 * 알림톡 예약발송할 리스트 조회
	 * */
	List<SOMap> selectSendMsgList(SOMap param);
	
	/**
	 * 알림톡 예약발송할 리스트
	 * */
	int insertMemberMsgReseve(SOMap param);
	
	int updateSendMsgList(SOMap param);
}
