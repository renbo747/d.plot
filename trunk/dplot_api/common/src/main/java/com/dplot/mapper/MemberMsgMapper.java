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
}
