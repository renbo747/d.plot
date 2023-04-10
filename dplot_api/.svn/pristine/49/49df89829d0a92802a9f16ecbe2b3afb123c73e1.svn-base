package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface RewareMapper {

	/**
	 * 재입고알림 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectRewareList(SOMap param);
	
	/**
	 * 재입고알림 목록 전체 수 조회
	 * @param param
	 * @return
	 */
	int selectRewareListCnt(SOMap param);

	/**
	 * 재입고알림 단건 삭제 처리
	 * @param param
	 * @return
	 */
	int updateRestockDel(SOMap param);

	/**
	 * 재입고알림 신청처리
	 * @param param
	 * @return
	 */
	int insertReware(SOMap param);

	/**
	 * 재입고알림 중복체크
	 * @param param
	 * @return
	 */
	int selectRewareDupCnt(SOMap param);

	/**
	 * 재입고 알림 발송 대상 리스트
	 */
	List<SOMap> selectRewareTargetList(SOMap param);


	int updateRestockNotiByIdx(SOMap param);
}
