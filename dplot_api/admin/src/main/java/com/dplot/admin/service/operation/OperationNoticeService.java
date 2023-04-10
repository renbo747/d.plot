package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 공지사항 Service
 * @fileName	: Service.java
 * @author		: LKW
 * @date		: 2022.02.28
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.02.28	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface OperationNoticeService {

	/**
	 * 운영관리 공지사항 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectNoticeList(SOMap params) throws Exception;

	/**
	 * 운영관리 공지사항 사용여부 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateNotice(SOMap params) throws Exception;

	/**
	 * 운영관리 공지사항 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectNoticeExcelList(SOMap params) throws Exception;

	/**
	 * 운영관리 공지사항 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveNotice(SOMap params) throws Exception;

	/**
	 * 운영관리 공지사항 상세조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectNoticeDetail(SOMap params) throws Exception;

	/**
	 * 운영관리 공지사항 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap modifyNotice(SOMap params) throws Exception;

	/**
	 * 운영관리 공지사항 삭제
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap deleteNotice(SOMap params) throws Exception;

	/**
	 * 운영관리 공지사항 변경시 알람용 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkNotice(SOMap params) throws Exception;

}
